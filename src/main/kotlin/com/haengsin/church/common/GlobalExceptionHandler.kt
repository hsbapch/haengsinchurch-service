package com.haengsin.church.common

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.haengsin.church.common.component.DiscordNotifier
import com.haengsin.church.common.exception.AccessDeniedException
import com.haengsin.church.common.exception.NotFoundException
import com.haengsin.church.configuration.filter.BaseFilter
import com.haengsin.church.configuration.filter.CachedBodyHttpServletRequest
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.resource.NoResourceFoundException
import java.time.OffsetDateTime
import java.time.ZoneOffset


@RestControllerAdvice
class GlobalExceptionHandler(
    private val discordNotifier: DiscordNotifier,

    ) {

    val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @Profile("prod")
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception, request: HttpServletRequest): ResponseEntity<Map<String, Any>> {

        // 노이즈(정적 리소스 404) 제외
        if (e is NoResourceFoundException && request.requestURI in IGNORED_PATHS) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("message" to "not found"))
        }

        val method = request.method
        val fullUrl = buildString {
            append(request.requestURL)
            request.queryString?.let { append("?").append(it) }
        }

        // multipart는 애초에 BaseFilter에서 제외됐지만, 방어적으로 한 번 더
        val isMultipart = request.contentType?.startsWith("multipart/") == true

        val traceId = request.getAttribute(BaseFilter.ATTR_TRACE_ID) as String?
        val body = if (!isMultipart) (request.getAttribute(BaseFilter.ATTR_CACHED_REQUEST_BODY) as String?) else null

        val bodySafe = body
            ?.let(::maskSecrets)
            ?.take(4000)

        val errorText = buildString {
            appendLine("TIME(ASIA/SEOUL): ${OffsetDateTime.now(ZoneOffset.ofHours(9))}")
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

        discordNotifier.send(title = "Server Error (500)", description = errorText)

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(mapOf("message" to "internal error"))
    }

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


    private fun maskSecrets(raw: String): String {
        var masked = raw

        val jsonKeys = listOf(
            "password",
        )

        jsonKeys.forEach { key ->
            masked = masked.replace(
                Regex(
                    "(\"$key\"\\s*:\\s*\")([^\"]+)(\")",
                    RegexOption.IGNORE_CASE
                ),
                "$1***$3"
            )
        }

        return masked
    }



    @ExceptionHandler(HttpMessageNotReadableException::class)
    protected fun handleException(e: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        log.error(ERROR_LOG_MESSAGE, e.javaClass.simpleName, e.message, e)

        return when (
            val caused = e.cause) {
            is MismatchedInputException -> {
                val parameterPath = caused.path.joinToString(separator = ".") { ref ->
                    ref.fieldName ?: "[${ref.index}]"
                }
                createErrorResponse(
                    httpStatus = HttpStatus.BAD_REQUEST,
                    message = "The \'$parameterPath\' field is missing.",
                )
            }

            null -> {
                createErrorResponse(
                    httpStatus = HttpStatus.BAD_REQUEST,
                    message = "Required request body is missing or invalid.",
                )
            }

            else -> {
                createErrorResponse(
                    httpStatus = HttpStatus.BAD_REQUEST,
                    message = e.message ?: "",
                )
            }
        }
    }

    @ExceptionHandler(NotFoundException::class)
    protected fun handleException(e: NotFoundException): ResponseEntity<ErrorResponse> =
        createErrorResponse(
            httpStatus = HttpStatus.NOT_FOUND,
            message = e.message ?: "",
        )

    @ExceptionHandler(AccessDeniedException::class)
    protected fun handleException(e: AccessDeniedException): ResponseEntity<ErrorResponse> =
        createErrorResponse(
            httpStatus = HttpStatus.UNAUTHORIZED,
            message = e.message ?: "",
        )


    private fun createErrorResponse(
        httpStatus: HttpStatus, message: String
    ): ResponseEntity<ErrorResponse> = ResponseEntity.status(httpStatus).body(
        ErrorResponse(
            httpStatus, message
        )
    )

    companion object {
        private const val ERROR_LOG_MESSAGE = "[ERROR] {} : {}"
    }

}

data class ErrorResponse(
    val httpStatus: HttpStatus,
    val message: String
)
