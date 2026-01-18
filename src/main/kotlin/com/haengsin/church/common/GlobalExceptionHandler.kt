package com.haengsin.church.common

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.haengsin.church.common.component.DiscordNotifier
import com.haengsin.church.common.exception.AccessDeniedException
import com.haengsin.church.common.exception.NotFoundException
import com.haengsin.church.configuration.filter.BaseFilter
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.core.env.Environment
import org.springframework.core.env.Profiles
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.resource.NoResourceFoundException
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.Locale

@RestControllerAdvice
class GlobalExceptionHandler(
    private val discordNotifier: DiscordNotifier,
    private val env: Environment,
) {
    private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception, request: HttpServletRequest): ResponseEntity<Map<String, Any>> {

        if (shouldIgnoreNoResource(e, request.requestURI)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("message" to "not found"))
        }

        notifyIfProd(
            title = "Server Error (500)",
            e = e,
            request = request,
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
        )

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(mapOf("message" to "internal error"))
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleException(e: HttpMessageNotReadableException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        log.error(ERROR_LOG_MESSAGE, e.javaClass.simpleName, e.message, e)

         notifyIfProd("Bad Request (400)", e, request, HttpStatus.BAD_REQUEST)

        return when (val caused = e.cause) {
            is MismatchedInputException -> {
                val parameterPath = caused.path.joinToString(".") { ref ->
                    ref.fieldName ?: "[${ref.index}]"
                }
                createErrorResponse(HttpStatus.BAD_REQUEST, "The '$parameterPath' field is missing.")
            }
            null -> createErrorResponse(HttpStatus.BAD_REQUEST, "Required request body is missing or invalid.")
            else -> createErrorResponse(HttpStatus.BAD_REQUEST, e.message ?: "")
        }
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleException(e: NotFoundException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
          if (request.requestURI in IGNORED_PATHS) {
            return createErrorResponse(HttpStatus.NOT_FOUND, e.message ?: "")
        }

         notifyIfProd("Not Found (404)", e, request, HttpStatus.NOT_FOUND)

        return createErrorResponse(HttpStatus.NOT_FOUND, e.message ?: "")
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleException(e: AccessDeniedException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        notifyIfProd("Unauthorized (401)", e, request, HttpStatus.UNAUTHORIZED)

        return createErrorResponse(HttpStatus.UNAUTHORIZED, e.message ?: "")
    }


    private fun notifyIfProd(
        title: String,
        e: Exception,
        request: HttpServletRequest,
        httpStatus: HttpStatus,
    ) {
        if (!env.acceptsProfiles(Profiles.of("prod"))) return

        // NoResourceFoundException + ignore path는 알림 제외
        if (shouldIgnoreNoResource(e, request.requestURI)) return

        val method = request.method
        val fullUrl = buildString {
            append(request.requestURL)
            request.queryString?.let { append("?").append(it) }
        }

        val isMultipart = request.contentType?.lowercase(Locale.ROOT)?.startsWith("multipart/") == true

        val traceId = request.getAttribute(BaseFilter.ATTR_TRACE_ID) as String?
        val body = if (!isMultipart) request.getAttribute(BaseFilter.ATTR_CACHED_REQUEST_BODY) as String? else null

        val bodySafe = body?.let(::maskSecrets)?.take(4000)

        val text = buildString {
            appendLine("TIME(ASIA/SEOUL): ${OffsetDateTime.now(ZoneOffset.ofHours(9))}")
            appendLine("HTTP_STATUS: ${httpStatus.value()} ${httpStatus.name}")
            traceId?.let { appendLine("TRACE_ID: $it") }
            appendLine("METHOD: $method")
            appendLine("URL: $fullUrl")
            if (!bodySafe.isNullOrBlank()) {
                appendLine("BODY:")
                appendLine(bodySafe)
            }
            appendLine("ERROR: ${e::class.qualifiedName}")
            appendLine("MESSAGE: ${e.message}")
            appendLine("STACKTRACE:")
            appendLine(e.stackTraceToString().take(3500))
        }

        runCatching {
            discordNotifier.send(title = title, description = text)
        }.onFailure {
            // 알림 전송 실패는 서비스 동작에 영향 주면 안 됨
            log.warn("Failed to send discord notification: ${it.message}")
        }
    }

    private fun shouldIgnoreNoResource(e: Exception, uri: String): Boolean {
        if (e !is NoResourceFoundException) return false
        return uri in IGNORED_PATHS
    }

    private fun maskSecrets(raw: String): String {
        var masked = raw

        val jsonKeys = listOf("password", "passwd", "pwd", "token", "accessToken", "refreshToken", "secret", "apiKey")
        jsonKeys.forEach { key ->
            masked = masked.replace(
                Regex("(\"$key\"\\s*:\\s*\")([^\"]+)(\")", RegexOption.IGNORE_CASE),
                "$1***$3"
            )
        }

        // Query string token류
        masked = masked.replace(
            Regex("([?&](token|access_token|refresh_token)=)([^&\\s]+)", RegexOption.IGNORE_CASE),
            "$1***"
        )

        return masked
    }

    private fun createErrorResponse(httpStatus: HttpStatus, message: String): ResponseEntity<ErrorResponse> =
        ResponseEntity.status(httpStatus).body(ErrorResponse(httpStatus, message))

    companion object {
        private const val ERROR_LOG_MESSAGE = "[ERROR] {} : {}"

        private val IGNORED_PATHS = setOf(
            "/robots.txt",
            "/favicon.ico",
            "/sitemap.xml",
            "/ads.txt",
            "/humans.txt",
            "/security.txt",
            "/apple-touch-icon.png",
            "/apple-touch-icon-precomposed.png",
            "/manifest.json"
        )
    }
}

data class ErrorResponse(
    val httpStatus: HttpStatus,
    val message: String
)

