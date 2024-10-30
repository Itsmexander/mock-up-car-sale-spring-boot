package com.example.test.service;

import com.example.test.dao.UserDao;
import com.example.test.dao.UserDaoImpl;
import com.example.test.domain.User;
import com.example.test.dto.PasswordChangeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public UserService(UserDaoImpl userDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.saveUser(user);
    }


    public Optional<User> getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userDao.getUserByEmail(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        User foundUser = user.get();
        return new org.springframework.security.core.userdetails.User(foundUser.getEmail(), foundUser.getPassword(), Collections.emptyList());
    }

    public void changePassword(PasswordChangeRequest request) {
        userDao.changePassword(request);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
}