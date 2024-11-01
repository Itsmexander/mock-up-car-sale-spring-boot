package com.example.test.validator;

import com.example.test.domain.User;

public class UserValidator implements Validator<User> {

    @Override
    public void validate(User user) throws ValidatorException {
        if(user.getFirstname() == null || user.getFirstname().isEmpty()) {
            throw new ValidatorException("Firstname is required");
        }
        if(user.getSurname() == null || user.getSurname().isEmpty()) {
            throw new ValidatorException("Surname is required");
        }
        if(user.getAddress() == null || user.getAddress().isEmpty()) {
            throw new ValidatorException("Address is required");
        }
        if(user.getTelno() == null || user.getTelno().isEmpty()) {
            throw new ValidatorException("Telephone number is required");
        }
        if(user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new ValidatorException("Email is required");
        }
    }
}
