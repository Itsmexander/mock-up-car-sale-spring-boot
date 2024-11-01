package com.example.test.controller;


import com.example.test.dto.PasswordChangeRequest;
import com.example.test.domain.User;
import com.example.test.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserServiceImpl userServiceImpl;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserServiceImpl userServiceImpl, AuthenticationManager authenticationManager) {
        this.userServiceImpl = userServiceImpl;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public void registerUser(@RequestBody User user) {
        userServiceImpl.saveUser(user);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "User logged in successfully";
    }
    @PostMapping("/passwordChange")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest request) {
        userServiceImpl.changePassword(request);
        return ResponseEntity.ok("Password changed successfully");
    }
    @GetMapping("/test/getalluser")
    public List<User> getAllUser() {
        return userServiceImpl.getAllUsers();
    }
}