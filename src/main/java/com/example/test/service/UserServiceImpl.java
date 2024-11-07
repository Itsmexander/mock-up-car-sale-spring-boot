package com.example.test.service;

import com.example.test.dao.UserDao;
import com.example.test.dao.UserDaoImpl;
import com.example.test.domain.User;
import com.example.test.dto.PasswordChangeRequest;
import com.example.test.validator.UserValidator;
import com.example.test.exception.ValidatorException;
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
public class UserServiceImpl implements UserService,UserDetailsService {

    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public UserServiceImpl(UserDaoImpl userDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void saveUser(User user) {
        UserValidator validator = new UserValidator();
        try{
            validator.validate(user);
        } catch (ValidatorException e) {
            System.err.println("Validation failed:"+e.getMessage());
            return;
        }
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

    @Override
    public void changePassword(PasswordChangeRequest request) {
       userDao.changePassword(request);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
}