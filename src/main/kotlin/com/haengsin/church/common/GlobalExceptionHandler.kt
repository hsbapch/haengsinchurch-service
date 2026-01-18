package com.haengsin.church.common

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.haengsin.church.common.component.DiscordNotifier
import com.haengsin.church.common.exception.AccessDeniedException
import com.haengsin.church.common.exception.NotFoundException
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
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
        val method = request.method
        val fullUrl = buildString {
            append(request.requestURL)
            request.queryString?.let { append("?").append(it) }
        }

//        val body = (request.getAttribute(RequestBodyCachingFilter.ATTR_CACHED_BODY) as String?)
//        val bodySafe = body
//            ?.let(::maskSecrets)
//            ?.take(4000) // 디스코드/운영 안전 컷

        val errorText = buildString {
            appendLine("TIME(ASIA/SEOUL): ${OffsetDateTime.now(ZoneOffset.ofHours(9))}")
            appendLine("METHOD: $method")
            appendLine("URL: $fullUrl")
//            if (!bodySafe.isNullOrBlank()) {
//                appendLine("BODY:")
//                appendLine(bodySafe)
//            }
            appendLine("ERROR: ${e::class.qualifiedName}")
            appendLine("MESSAGE: ${e.message}")
            appendLine("STACKTRACE:")
            appendLine(e.stackTraceToString().take(3500))
        }

        discordNotifier.send(title = "Server Error (500)", description = errorText)

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(mapOf("message" to "internal error"))
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
