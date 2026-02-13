package com.example.test.validator;

import com.example.test.domain.User;
import com.example.test.exception.ValidatorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {

    private UserValidator validator;

    @BeforeEach
    void setUp() {
        validator = new UserValidator();
    }

    private User validUser() {
        User user = new User();
        user.setFirstname("John");
        user.setSurname("Doe");
        user.setAddress("123 Main St");
        user.setTelno("0123456789");
        user.setEmail("john@example.com");
        user.setPassword("password123");
        return user;
    }

    @Test
    void shouldPassValidation_whenUserIsValid() {
        assertDoesNotThrow(() -> validator.validate(validUser()));
    }

    @Test
    void shouldThrow_whenFirstnameIsNull() {
        User user = validUser();
        user.setFirstname(null);
        ValidatorException ex = assertThrows(ValidatorException.class, () -> validator.validate(user));
        assertEquals("Firstname is required", ex.getMessage());
    }

    @Test
    void shouldThrow_whenFirstnameIsEmpty() {
        User user = validUser();
        user.setFirstname("");
        ValidatorException ex = assertThrows(ValidatorException.class, () -> validator.validate(user));
        assertEquals("Firstname is required", ex.getMessage());
    }

    @Test
    void shouldThrow_whenSurnameIsNull() {
        User user = validUser();
        user.setSurname(null);
        ValidatorException ex = assertThrows(ValidatorException.class, () -> validator.validate(user));
        assertEquals("Surname is required", ex.getMessage());
    }

    @Test
    void shouldThrow_whenSurnameIsEmpty() {
        User user = validUser();
        user.setSurname("");
        ValidatorException ex = assertThrows(ValidatorException.class, () -> validator.validate(user));
        assertEquals("Surname is required", ex.getMessage());
    }

    @Test
    void shouldThrow_whenAddressIsNull() {
        User user = validUser();
        user.setAddress(null);
        ValidatorException ex = assertThrows(ValidatorException.class, () -> validator.validate(user));
        assertEquals("Address is required", ex.getMessage());
    }

    @Test
    void shouldThrow_whenAddressIsEmpty() {
        User user = validUser();
        user.setAddress("");
        ValidatorException ex = assertThrows(ValidatorException.class, () -> validator.validate(user));
        assertEquals("Address is required", ex.getMessage());
    }

    @Test
    void shouldThrow_whenTelnoIsNull() {
        User user = validUser();
        user.setTelno(null);
        ValidatorException ex = assertThrows(ValidatorException.class, () -> validator.validate(user));
        assertEquals("Telephone number is required", ex.getMessage());
    }

    @Test
    void shouldThrow_whenTelnoIsEmpty() {
        User user = validUser();
        user.setTelno("");
        ValidatorException ex = assertThrows(ValidatorException.class, () -> validator.validate(user));
        assertEquals("Telephone number is required", ex.getMessage());
    }

    @Test
    void shouldThrow_whenEmailIsNull() {
        User user = validUser();
        user.setEmail(null);
        ValidatorException ex = assertThrows(ValidatorException.class, () -> validator.validate(user));
        assertEquals("Email is required", ex.getMessage());
    }

    @Test
    void shouldThrow_whenEmailIsEmpty() {
        User user = validUser();
        user.setEmail("");
        ValidatorException ex = assertThrows(ValidatorException.class, () -> validator.validate(user));
        assertEquals("Email is required", ex.getMessage());
    }
}
