package com.example.test.dto;

import com.example.test.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class UserResponse {
    private long id;
    private String firstname;
    private String surname;
    private String address;
    private String telno;
    private String email;
    private Timestamp lastModifiedTimestamp;
    private Timestamp creationTimestamp;

    public static UserResponse fromUser(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFirstname(user.getFirstname());
        response.setSurname(user.getSurname());
        response.setAddress(user.getAddress());
        response.setTelno(user.getTelno());
        response.setEmail(user.getEmail());
        response.setLastModifiedTimestamp(user.getLastModifiedTimestamp());
        response.setCreationTimestamp(user.getCreationTimestamp());
        return response;
    }
}
