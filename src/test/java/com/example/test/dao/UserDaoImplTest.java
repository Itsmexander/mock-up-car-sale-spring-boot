package com.example.test.dao;

import com.example.test.domain.User;
import com.example.test.dto.PasswordChangeRequest;
import com.example.test.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private UserDaoJdbcImpl userDao;

    @BeforeEach
    void setUp() {
        userDao = new UserDaoJdbcImpl(jdbcTemplate, passwordEncoder);
    }

    private User sampleUser() {
        User user = new User();
        user.setId(1L);
        user.setFirstname("John");
        user.setSurname("Doe");
        user.setAddress("123 Main St");
        user.setTelno("0123456789");
        user.setEmail("john@example.com");
        user.setPassword("hashedPassword");
        return user;
    }

    @Test
    void getUserByEmail_found() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq("john@example.com")))
                .thenReturn(List.of(sampleUser()));

        var result = userDao.getUserByEmail("john@example.com");

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getFirstname());
    }

    @Test
    void getUserByEmail_notFound() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq("nobody@example.com")))
                .thenReturn(Collections.emptyList());

        var result = userDao.getUserByEmail("nobody@example.com");

        assertFalse(result.isPresent());
    }

    @Test
    void getAllUsers_returnsList() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(List.of(sampleUser()));

        var result = userDao.getAllUsers();

        assertEquals(1, result.size());
    }

    @Test
    void saveUser_executesSql() {
        when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class)))
                .thenReturn(1);

        userDao.saveUser(sampleUser());

        verify(jdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
    }

    @Test
    void updateUser_executesSql() {
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(1);

        userDao.updateUser(sampleUser());

        verify(jdbcTemplate).update(anyString(), any(), any(), any(), any(), any(), any(), any(), any());
    }

    @Test
    void deleteUser_executesSql() {
        when(jdbcTemplate.update(anyString(), eq(1L))).thenReturn(1);

        userDao.deleteUser(sampleUser());

        verify(jdbcTemplate).update(anyString(), eq(1L));
    }

    @Test
    void changePassword_success() {
        User user = sampleUser();
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1L)))
                .thenReturn(List.of(user));
        when(passwordEncoder.matches("oldPass", "hashedPassword")).thenReturn(true);
        when(passwordEncoder.encode("newPass")).thenReturn("newHashedPassword");
        when(jdbcTemplate.update(anyString(), eq("newHashedPassword"), any(), eq(1L))).thenReturn(1);

        PasswordChangeRequest request = new PasswordChangeRequest();
        request.setId(1L);
        request.setOldPassword("oldPass");
        request.setNewPassword("newPass");

        assertDoesNotThrow(() -> userDao.changePassword(request));
        verify(passwordEncoder).encode("newPass");
    }

    @Test
    void changePassword_wrongPassword() {
        User user = sampleUser();
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1L)))
                .thenReturn(List.of(user));
        when(passwordEncoder.matches("wrongPass", "hashedPassword")).thenReturn(false);

        PasswordChangeRequest request = new PasswordChangeRequest();
        request.setId(1L);
        request.setOldPassword("wrongPass");
        request.setNewPassword("newPass");

        assertThrows(BadCredentialsException.class, () -> userDao.changePassword(request));
    }

    @Test
    void changePassword_userNotFound() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(999L)))
                .thenReturn(Collections.emptyList());

        PasswordChangeRequest request = new PasswordChangeRequest();
        request.setId(999L);
        request.setOldPassword("oldPass");
        request.setNewPassword("newPass");

        assertThrows(UserNotFoundException.class, () -> userDao.changePassword(request));
    }
}
