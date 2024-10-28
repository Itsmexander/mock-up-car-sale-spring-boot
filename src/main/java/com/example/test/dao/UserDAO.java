package com.example.test.dao;

import com.example.test.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {

    Optional<User> getUserById(long id);
    List<User> getAllUsers();
    void saveUser(User user);
    void updateUser(User user);
    void deleteUser(User user);
}