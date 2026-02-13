package com.example.test.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void handleValidatorException() {
        ValidatorException ex = new ValidatorException("Car name cannot be empty");
        ResponseEntity<Map<String, String>> response = handler.handleValidatorException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Car name cannot be empty", response.getBody().get("error"));
    }

    @Test
    void handleCustomValidationException() {
        CustomValidationException ex = new CustomValidationException("Validation failed");
        ResponseEntity<Map<String, String>> response = handler.handleCustomValidationException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Validation failed", response.getBody().get("error"));
    }

    @Test
    void handleIllegalArgumentException() {
        IllegalArgumentException ex = new IllegalArgumentException("Invalid parameter");
        ResponseEntity<Map<String, String>> response = handler.handleIllegalArgumentException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid parameter", response.getBody().get("error"));
    }

    @Test
    void handleRuntimeException() {
        RuntimeException ex = new RuntimeException("Something went wrong");
        ResponseEntity<Map<String, String>> response = handler.handleRuntimeException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Something went wrong", response.getBody().get("error"));
    }
}
