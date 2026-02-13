package com.example.test.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorExceptionTest {

    @Test
    void messageIsPreserved() {
        ValidatorException ex = new ValidatorException("test message");
        assertEquals("test message", ex.getMessage());
    }

    @Test
    void isRuntimeException() {
        ValidatorException ex = new ValidatorException("test");
        assertInstanceOf(RuntimeException.class, ex);
    }
}
