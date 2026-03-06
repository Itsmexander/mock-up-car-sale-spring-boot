package com.example.test.service;

import com.example.test.dao.UserDao;
import com.example.test.domain.User;
import com.example.test.dto.PasswordChangeRequest;
import com.example.test.exception.DuplicateEmailException;
import com.example.test.validator.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
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
        log.debug("[UserService] saveUser - email={}", user.getEmail());
        UserValidator validator = new UserValidator();
        validator.validate(user);
        if (userDao.getUserByEmail(user.getEmail()).isPresent()) {
            log.warn("[UserService] saveUser - email={} already in use", user.getEmail());
            throw new DuplicateEmailException("Email already in use");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.saveUser(user);
        log.debug("[UserService] saveUser - email={} saved", user.getEmail());
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        log.debug("[UserService] getUserByEmail - email={}", email);
        Optional<User> result = userDao.getUserByEmail(email);
        log.debug("[UserService] getUserByEmail - email={}, result={}", email, result.isPresent() ? "found" : "empty");
        return result;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("[UserService] loadUserByUsername - username={}", username);
        Optional<User> user = userDao.getUserByEmail(username);
        if (user.isEmpty()) {
            log.debug("[UserService] loadUserByUsername - username={} not found", username);
            throw new UsernameNotFoundException("User not found");
        }
        User foundUser = user.get();
        log.debug("[UserService] loadUserByUsername - username={} loaded", username);
        return new org.springframework.security.core.userdetails.User(foundUser.getEmail(), foundUser.getPassword(), Collections.emptyList());
    }

    @Override
    public void changePassword(PasswordChangeRequest request) {
        log.debug("[UserService] changePassword - userId={}", request.getId());
        userDao.changePassword(request);
        log.debug("[UserService] changePassword - userId={} changed", request.getId());
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userDao.getAllUsers();
        log.debug("[UserService] getAllUsers - returned {} users", users.size());
        return users;
    }
}
