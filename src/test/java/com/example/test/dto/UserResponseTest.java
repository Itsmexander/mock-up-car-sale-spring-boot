package com.example.test.dto;

import com.example.test.domain.User;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class UserResponseTest {

    @Test
    void fromUser_mapsAllFieldsExceptPassword() {
        User user = new User();
        user.setId(1L);
        user.setFirstname("John");
        user.setSurname("Doe");
        user.setAddress("123 Main St");
        user.setTelno("0123456789");
        user.setEmail("john@example.com");
        user.setPassword("secret");
        Timestamp now = new Timestamp(System.currentTimeMillis());
        user.setCreationTimestamp(now);
        user.setLastModifiedTimestamp(now);

        UserResponse response = UserResponse.fromUser(user);

        assertEquals(1L, response.getId());
        assertEquals("John", response.getFirstname());
        assertEquals("Doe", response.getSurname());
        assertEquals("123 Main St", response.getAddress());
        assertEquals("0123456789", response.getTelno());
        assertEquals("john@example.com", response.getEmail());
        assertEquals(now, response.getCreationTimestamp());
        assertEquals(now, response.getLastModifiedTimestamp());
    }
}
