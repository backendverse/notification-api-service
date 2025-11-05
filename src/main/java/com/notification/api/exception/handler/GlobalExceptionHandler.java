package com.notification.api.exception.handler;

import com.notification.api.exception.AbstractException;
import com.notification.api.exception.ResourceNotFoundException;
import com.notification.api.exception.ValidationException;
import com.notification.api.utils.CommonUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException exception) {
        return genericExceptionHandler(
                exception,
                () -> ResponseEntity.badRequest().body(exception.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotException(ResourceNotFoundException exception) {
        return genericExceptionHandler(
                exception,
                () -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage()));
    }

    public ResponseEntity<String> genericExceptionHandler(final AbstractException exception,
                                                          final Supplier<ResponseEntity<String>> runner) {
        if (CommonUtils.isNotEmpty(exception.getStatusCode())) {
            return ResponseEntity.status(exception.getStatusCode()).body(exception.getErrorMessage());
        }
        return runner.get();
    }

}
