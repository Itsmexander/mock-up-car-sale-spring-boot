package com.example.test.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "surname")
    private String surname;

    @Column(name = "address")
    private String address;

    @Column(name = "telno")
    private String telno;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @UpdateTimestamp
    @Column(name = "last_modified_timestamp")
    private Timestamp lastModifiedTimestamp;

    @CreationTimestamp
    @Column(name = "creation_timestamp", updatable = false)
    private Timestamp creationTimestamp;
}
