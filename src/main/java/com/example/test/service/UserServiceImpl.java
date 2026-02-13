package com.example.test.service;

import com.example.test.dao.UserDao;
import com.example.test.domain.User;
import com.example.test.dto.PasswordChangeRequest;
import com.example.test.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void saveUser(User user) {
        UserValidator validator = new UserValidator();
        validator.validate(user);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.saveUser(user);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userDao.getUserByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        User foundUser = user.get();
        return new org.springframework.security.core.userdetails.User(foundUser.getEmail(), foundUser.getPassword(), Collections.emptyList());
    }

    @Override
    public void changePassword(PasswordChangeRequest request) {
        userDao.changePassword(request);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
}
