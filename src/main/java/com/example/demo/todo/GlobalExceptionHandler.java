package com.example.demo.todo;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> handleResponseStatusException(
            ResponseStatusException exception,
            HttpServletRequest request
    ) {
        return ResponseEntity
                .status(exception.getStatusCode())
                .body(new ApiError(
                        exception.getStatusCode().value(),
                        exception.getStatusCode().toString(),
                        exception.getReason(),
                        request.getRequestURI()
                ));
    }
}
