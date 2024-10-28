package com.example.test.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;


@Getter
@Setter
@ToString
public class User {

    private long id;
    private String firstName;
    private String surname;
    private String address ;
    private String telNO ;
    private String email;
    private String password;

    private Timestamp lastModifiedTimestamp;
    private Timestamp creationTimestamp;
}
