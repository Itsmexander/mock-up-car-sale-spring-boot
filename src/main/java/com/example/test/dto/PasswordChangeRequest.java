package com.example.test.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordChangeRequest {
    private long id;
    private String oldPassword;
    private String newPassword;
}