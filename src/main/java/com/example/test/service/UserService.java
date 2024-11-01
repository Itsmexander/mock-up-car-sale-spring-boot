package com.example.test.service;

import com.example.test.domain.User;
import com.example.test.dto.PasswordChangeRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void saveUser(User user);
    Optional<User> getUserByEmail(String email);
    void changePassword(PasswordChangeRequest passwordChangeRequest);
    List<User> getAllUsers();
}
