package com.example.test.validator;

import com.example.test.exception.ValidatorException;

public interface Validator <T>{
    void validate(T t) throws ValidatorException;
}
