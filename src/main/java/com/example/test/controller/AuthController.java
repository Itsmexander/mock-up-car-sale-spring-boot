package com.example.test.controller;

import com.example.test.domain.User;
import com.example.test.exception.UserNotFoundException;
import com.example.test.dto.LoginResponse;
import com.example.test.dto.PasswordChangeRequest;
import com.example.test.dto.UserResponse;
import com.example.test.security.JwtUtil;
import com.example.test.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody User user) {
        log.info("[AuthController] POST /auth/register - email={}", user.getEmail());
        userService.saveUser(user);
        log.info("[AuthController] POST /auth/register - 201 CREATED");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody User user) {
        log.info("[AuthController] POST /auth/login - email={}", user.getEmail());
        User found = userService.getUserByEmail(user.getEmail())
                .orElseThrow(() -> {
                    log.warn("[AuthController] POST /auth/login - no user found for email={}", user.getEmail());
                    return new UserNotFoundException("User not found");
                });
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generateToken(user.getEmail());
        log.info("[AuthController] POST /auth/login - 200 OK");
        return ResponseEntity.ok(new LoginResponse(token, found.getId()));
    }

    @PostMapping("/passwordChange")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeRequest request) {
        log.info("[AuthController] POST /auth/passwordChange - userId={}", request.getId());
        userService.changePassword(request);
        log.info("[AuthController] POST /auth/passwordChange - 200 OK");
        return ResponseEntity.ok("Password changed successfully");
    }

    @GetMapping("/test/getalluser")
    public ResponseEntity<List<UserResponse>> getAllUser() {
        log.info("[AuthController] GET /auth/test/getalluser");
        List<UserResponse> users = userService.getAllUsers().stream()
                .map(UserResponse::fromUser)
                .collect(Collectors.toList());
        log.info("[AuthController] GET /auth/test/getalluser - 200 OK, returned {} users", users.size());
        return ResponseEntity.ok(users);
    }
}
