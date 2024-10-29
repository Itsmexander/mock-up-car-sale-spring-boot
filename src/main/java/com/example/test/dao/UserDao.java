package com.example.test.dao;

import com.example.test.domain.User;
import com.example.test.dto.PasswordChangeRequest;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> getUserByEmail(String email);
    List<User> getAllUsers();
    void saveUser(User user);
    void updateUser(User user);
    void deleteUser(User user);
    Connection getConnection();
    void changePassword(PasswordChangeRequest request);
}