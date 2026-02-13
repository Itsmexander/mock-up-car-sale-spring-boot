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
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carId")
    private long carId;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private float price;

    @Column(name = "notation")
    private String notation;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "manufacturedYear")
    private int manufacturedYear;

    @UpdateTimestamp
    @Column(name = "last_modified_timestamp")
    private Timestamp lastModifiedTimestamp;

    @CreationTimestamp
    @Column(name = "creation_timestamp", updatable = false)
    private Timestamp creationTimestamp;
}
