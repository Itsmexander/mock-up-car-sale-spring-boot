package com.example.test.validator;

public interface Validator <T>{
    void validate(T t) throws ValidatorException;
}
