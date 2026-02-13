package com.example.test.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomValidationExceptionTest {

    @Test
    void messageIsPreserved() {
        CustomValidationException ex = new CustomValidationException("test message");
        assertEquals("test message", ex.getMessage());
    }

    @Test
    void isRuntimeException() {
        CustomValidationException ex = new CustomValidationException("test");
        assertInstanceOf(RuntimeException.class, ex);
    }
}
