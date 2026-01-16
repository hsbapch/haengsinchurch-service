package com.haengsin.church.common

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.haengsin.church.common.exception.AccessDeniedException
import com.haengsin.church.common.exception.NotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class GlobalExceptionHandler {

    val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

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
