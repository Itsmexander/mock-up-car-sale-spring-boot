package com.example.test.controller;

import com.example.test.domain.User;
import com.example.test.dto.PasswordChangeRequest;
import com.example.test.exception.DuplicateEmailException;
import com.example.test.exception.UserNotFoundException;
import com.example.test.exception.ValidatorException;
import com.example.test.security.JwtUtil;
import com.example.test.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private User sampleUser() {
        User user = new User();
        user.setId(1L);
        user.setFirstname("John");
        user.setSurname("Doe");
        user.setAddress("123 Main St");
        user.setTelno("0123456789");
        user.setEmail("john@example.com");
        user.setPassword("password123");
        user.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));
        return user;
    }

    @Test
    void registerUser_success() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleUser())))
                .andExpect(status().isCreated());
    }

    @Test
    void registerUser_validationFailure() throws Exception {
        User user = new User();
        user.setFirstname("");
        doThrow(new ValidatorException("Firstname is required"))
                .when(userService).saveUser(any(User.class));

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerUser_duplicateEmail() throws Exception {
        doThrow(new DuplicateEmailException("Email already in use"))
                .when(userService).saveUser(any(User.class));

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleUser())))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Email already in use"));
    }

    @Test
    void loginUser_success() throws Exception {
        User user = sampleUser();
        Authentication auth = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        when(jwtUtil.generateToken(user.getEmail())).thenReturn("mock-token");
        when(userService.getUserByEmail(user.getEmail())).thenReturn(Optional.of(user));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mock-token"))
                .andExpect(jsonPath("$.userId").value(1));
    }

    @Test
    void loginUser_unknownEmail() throws Exception {
        User user = sampleUser();
        when(userService.getUserByEmail(user.getEmail()))
                .thenThrow(new UserNotFoundException("User not found"));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("User not found"));
    }

    @Test
    void loginUser_wrongPassword() throws Exception {
        User user = sampleUser();
        when(userService.getUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Wrong password"));
    }

    @Test
    void changePassword_success() throws Exception {
        PasswordChangeRequest request = new PasswordChangeRequest();
        request.setId(1L);
        request.setOldPassword("old");
        request.setNewPassword("new");

        mockMvc.perform(post("/auth/passwordChange")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Password changed successfully"));
    }

    @Test
    void getAllUser_noPasswordExposed() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(sampleUser()));

        mockMvc.perform(get("/auth/test/getalluser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstname").value("John"))
                .andExpect(jsonPath("$[0].email").value("john@example.com"))
                .andExpect(jsonPath("$[0].password").doesNotExist());
    }
}
