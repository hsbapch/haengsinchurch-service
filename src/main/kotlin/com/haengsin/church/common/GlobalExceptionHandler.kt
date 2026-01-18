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
        if (shouldIgnoreNoResource(e, request.requestURI)) return

        val isMultipart = request.contentType?.lowercase(Locale.ROOT)?.startsWith("multipart/") == true
        val traceId = request.getAttribute(BaseFilter.ATTR_TRACE_ID) as String?
        val body = if (!isMultipart) request.getAttribute(BaseFilter.ATTR_CACHED_REQUEST_BODY) as String? else null
        val bodySafe = body?.let(::maskSecrets)?.take(4000)

        val ev = buildErrorEvent(
            title = title,
            httpStatus = httpStatus,
            e = e,
            request = request,
            traceId = traceId,
            bodySafe = bodySafe
        )

        val payload = toDiscordWebhookPayload(ev)
        discordNotifier.send(payload)
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
            "/owa/auth/logon.aspx",
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

    data class ErrorEvent(
        val title: String,
        val status: HttpStatus,
        val timeKst: String,
        val traceId: String?,
        val method: String,
        val url: String,
        val requestBody: String?,
        val errorClass: String,
        val errorMessage: String?,
        val stacktrace: String?
    )

    private fun buildErrorEvent(
        title: String,
        httpStatus: HttpStatus,
        e: Exception,
        request: HttpServletRequest,
        traceId: String?,
        bodySafe: String?,
    ): ErrorEvent {
        val fullUrl = buildString {
            append(request.requestURL)
            request.queryString?.let { append("?").append(it) }
        }

        val timeKst = OffsetDateTime.now(ZoneOffset.ofHours(9)).toString()

        val includeStack = httpStatus.is5xxServerError

        return ErrorEvent(
            title = title,
            status = httpStatus,
            timeKst = timeKst,
            traceId = traceId,
            method = request.method,
            url = fullUrl,
            requestBody = bodySafe,
            errorClass = e::class.qualifiedName ?: e::class.java.name,
            errorMessage = e.message,
            stacktrace = if (includeStack) e.stackTraceToString().take(3000) else null
        )
    }

    private fun toDiscordWebhookPayload(ev: ErrorEvent): Map<String, Any> {
        fun code(lang: String, s: String) = "```$lang\n$s\n```"

        val summary = buildString {
            appendLine("TIME(KST): ${ev.timeKst}")
            appendLine("STATUS: ${ev.status.value()} ${ev.status.name}")
            ev.traceId?.let { appendLine("TRACE_ID: $it") }
            appendLine("METHOD: ${ev.method}")
            appendLine("URL: ${ev.url}")
            appendLine("ERROR: ${ev.errorClass}")
            ev.errorMessage?.let { appendLine("MESSAGE: $it") }
        }

        val fields = mutableListOf<Map<String, Any>>()

        fields += mapOf(
            "name" to "📌 Summary",
            "value" to code("text", summary),
            "inline" to false
        )

        if (!ev.requestBody.isNullOrBlank()) {
            fields += mapOf(
                "name" to "📦 Request Body",
                "value" to code("json", ev.requestBody.take(1000)),
                "inline" to false
            )
        }

        if (!ev.stacktrace.isNullOrBlank()) {
            // 스포일러(||)로 감싸면 Discord에서 클릭해야 보이는 느낌이 됩니다.
            fields += mapOf(
                "name" to "🧵 Stacktrace (click to expand)",
                "value" to "||${code("text", ev.stacktrace)}||",
                "inline" to false
            )
        }

        // 상태별 색상 (Discord embed color는 int)
        val color = when {
            ev.status.is5xxServerError -> 15158332 // red
            ev.status.is4xxClientError -> 16753920 // orange
            else -> 3447003 // blue
        }

        return mapOf(
            "embeds" to listOf(
                mapOf(
                    "title" to ev.title,
                    "color" to color,
                    "fields" to fields,
                    // Discord timestamp는 ISO8601 UTC 권장
                    "timestamp" to OffsetDateTime.now(ZoneOffset.UTC).toString()
                )
            )
        )
    }

}

data class ErrorResponse(
    val httpStatus: HttpStatus,
    val message: String
)

