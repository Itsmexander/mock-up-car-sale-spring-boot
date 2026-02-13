package com.example.test.service;

import com.example.test.dao.UserDao;
import com.example.test.domain.User;
import com.example.test.dto.PasswordChangeRequest;
import com.example.test.exception.ValidatorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userDao, passwordEncoder);
    }

    private User validUser() {
        User user = new User();
        user.setId(1L);
        user.setFirstname("John");
        user.setSurname("Doe");
        user.setAddress("123 Main St");
        user.setTelno("0123456789");
        user.setEmail("john@example.com");
        user.setPassword("password123");
        return user;
    }

    @Test
    void saveUser_valid() {
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");

        userService.saveUser(validUser());

        verify(passwordEncoder).encode("password123");
        verify(userDao).saveUser(any(User.class));
    }

    @Test
    void saveUser_invalid_throwsException() {
        User user = new User();
        user.setFirstname("");

        assertThrows(ValidatorException.class, () -> userService.saveUser(user));
        verify(userDao, never()).saveUser(any());
    }

    @Test
    void getUserByEmail() {
        when(userDao.getUserByEmail("john@example.com")).thenReturn(Optional.of(validUser()));

        Optional<User> result = userService.getUserByEmail("john@example.com");

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getFirstname());
    }

    @Test
    void loadUserByUsername_found() {
        User user = validUser();
        when(userDao.getUserByEmail("john@example.com")).thenReturn(Optional.of(user));

        UserDetails result = userService.loadUserByUsername("john@example.com");

        assertEquals("john@example.com", result.getUsername());
    }

    @Test
    void loadUserByUsername_notFound() {
        when(userDao.getUserByEmail("nobody@example.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("nobody@example.com"));
    }

    @Test
    void changePassword() {
        PasswordChangeRequest request = new PasswordChangeRequest();
        request.setId(1L);
        request.setOldPassword("old");
        request.setNewPassword("new");

        userService.changePassword(request);

        verify(userDao).changePassword(request);
    }

    @Test
    void getAllUsers() {
        when(userDao.getAllUsers()).thenReturn(List.of(validUser()));

        List<User> result = userService.getAllUsers();

        assertEquals(1, result.size());
    }
}
