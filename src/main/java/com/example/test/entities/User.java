package com.example.test.entities;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "surname")
    private String surname;
    @Column(name = "address",columnDefinition = "VARCHAR(255)")
    private String address ;
    @Column(name = "telNO",columnDefinition = "VARCHAR(10)")
    private String telNO ;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    @LastModifiedDate
    @Column(name = "last_Update_timestamp")
    private Instant lastModifiedTimestamp;
    @CreationTimestamp
    @Column(name = "creation_timestamp")
    private Instant creationTimestamp;
}
