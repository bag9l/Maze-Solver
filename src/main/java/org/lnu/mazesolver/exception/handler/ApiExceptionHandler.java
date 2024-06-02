package org.lnu.mazesolver.exception.handler;

import org.lnu.mazesolver.exception.EntityNotExistsException;
import org.lnu.mazesolver.exception.InvalidTokenException;
import org.lnu.mazesolver.exception.PaymentRequiredException;
import org.lnu.mazesolver.exception.PermissionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(err ->
                errorMap.put(err.getField(), err.getDefaultMessage()));
        return errorMap;
    }

    @ExceptionHandler(EntityNotExistsException.class)
    protected ResponseEntity<Object> handleEntityNotExists(
            EntityNotExistsException exception) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(PermissionException.class)
    protected ResponseEntity<Object> handlePermissionException(
            PermissionException exception) {
        return buildResponseEntity(HttpStatus.FORBIDDEN, exception.getMessage());
    }

    @ExceptionHandler(IOException.class)
    protected ResponseEntity<Object> handleIOException(
            IOException exception) {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @ExceptionHandler(InvalidTokenException.class)
    protected ResponseEntity<Object> handleInvalidTokenException(
            InvalidTokenException exception) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(PaymentRequiredException.class)
    protected ResponseEntity<Object> handlePaymentRequiredException(
            PaymentRequiredException exception) {
        return buildResponseEntity(HttpStatus.PAYMENT_REQUIRED, exception.getMessage());
    }

    private ResponseEntity<Object> buildResponseEntity(HttpStatus paymentRequired, String exception) {
        ApiError apiError = new ApiError(paymentRequired);
        apiError.setMessage(exception);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
