package com.example.fraudsystem.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FraudException.class)
    public ResponseEntity<String> handleFraudException(FraudException e) {

        return ResponseEntity
                .badRequest()
                .body(e.getMessage());

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {

        return ResponseEntity
                .internalServerError()
                .body(e.getMessage());

    }
}
