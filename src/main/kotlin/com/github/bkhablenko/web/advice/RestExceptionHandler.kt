package com.github.bkhablenko.web.advice

import com.github.bkhablenko.web.model.ErrorResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.ConversionNotSupportedException
import org.springframework.beans.TypeMismatchException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.http.converter.HttpMessageNotWritableException
import org.springframework.security.access.AccessDeniedException
import org.springframework.validation.BindException
import org.springframework.web.ErrorResponseException
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingPathVariableException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.async.AsyncRequestTimeoutException
import org.springframework.web.multipart.support.MissingServletRequestPartException
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.LocalDateTime

@RestControllerAdvice
class RestExceptionHandler {

    /**
     * Translate framework-specific exceptions into a standardized error format.
     *
     * @see [ResponseEntityExceptionHandler.handleException]
     */
    @ExceptionHandler(
        HttpRequestMethodNotSupportedException::class,
        HttpMediaTypeNotSupportedException::class,
        HttpMediaTypeNotAcceptableException::class,
        MissingPathVariableException::class,
        MissingServletRequestParameterException::class,
        MissingServletRequestPartException::class,
        ServletRequestBindingException::class,
        MethodArgumentNotValidException::class,
        NoHandlerFoundException::class,
        AsyncRequestTimeoutException::class,
        ErrorResponseException::class,
    )
    fun handleErrorResponse(request: HttpServletRequest, exception: Exception): ResponseEntity<ErrorResponse> {
        exception as org.springframework.web.ErrorResponse

        val statusCode = exception.statusCode.value()
        return errorResponseOf(request, HttpStatus.valueOf(statusCode))
    }

    @ExceptionHandler(ConversionNotSupportedException::class)
    fun handleConversionNotSupportedException(request: HttpServletRequest) =
        errorResponseOf(request, INTERNAL_SERVER_ERROR)

    @ExceptionHandler(TypeMismatchException::class)
    fun handleTypeMismatchException(request: HttpServletRequest) =
        errorResponseOf(request, BAD_REQUEST)

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(request: HttpServletRequest) =
        errorResponseOf(request, BAD_REQUEST)

    @ExceptionHandler(HttpMessageNotWritableException::class)
    fun handleHttpMessageNotWritableException(request: HttpServletRequest) =
        errorResponseOf(request, INTERNAL_SERVER_ERROR)

    @ExceptionHandler(BindException::class)
    fun handleBindException(request: HttpServletRequest) =
        errorResponseOf(request, BAD_REQUEST)

    // Everything else
    @ExceptionHandler(Exception::class)
    fun handleException(request: HttpServletRequest, exception: Exception) =
        errorResponseOf(request, INTERNAL_SERVER_ERROR, "Something went awfully wrong.")

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(exception: AccessDeniedException) {
        // Let the filter chain deal with it
        throw exception
    }

    private fun errorResponseOf(request: HttpServletRequest, status: HttpStatus, message: String? = null) =
        ResponseEntity.status(status).body(
            ErrorResponse(
                timestamp = LocalDateTime.now(),
                status = status.value(),
                error = status.reasonPhrase,
                message = message ?: "",
                path = request.requestURI,
            )
        )
}
