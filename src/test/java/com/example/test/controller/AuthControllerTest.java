package com.example.test.controller;

import com.example.test.domain.User;
import com.example.test.dto.PasswordChangeRequest;
import com.example.test.exception.GlobalExceptionHandler;
import com.example.test.exception.ValidatorException;
import com.example.test.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.List;

import static org.hamcrest.Matchers.*;
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
        User user = sampleUser();

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
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
    void loginUser_success() throws Exception {
        User user = sampleUser();
        Authentication auth = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(auth);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().string("User logged in successfully"));
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
        User user = sampleUser();
        when(userService.getAllUsers()).thenReturn(List.of(user));

        mockMvc.perform(get("/auth/test/getalluser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstname").value("John"))
                .andExpect(jsonPath("$[0].email").value("john@example.com"))
                .andExpect(jsonPath("$[0].password").doesNotExist());
    }
}
