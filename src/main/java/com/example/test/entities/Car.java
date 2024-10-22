package com.example.test.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity(name = "car")
@EntityListeners(AuditingEntityListener.class)
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "car_id")
    private long carId;

    @Column(name = "car_name", columnDefinition = "VARCHAR(128)")
    private String carName;

    @Column(name = "price", columnDefinition = "FLOAT")
    private float price;

    //    @Lob
    @Column(name = "car_other_description"/*, columnDefinition = "CLOB"*/)
    private String carDesc;
//    @Column(name = "car_type")
//    private String carType;
//    @Column(name = "gear_type")
//    private String gearType;
//    @Column(name = "fuel_type")
//    private String fuelType;
//    @Column(name = "wheel_count")
//    private String wheelCount;
    @Column(name = "manufacturer")
    private String manufacturer;
    @Column(name = "manufactured_year")
    private int manufacturedYear;

    @LastModifiedDate
    @Column(name = "lastUpdate_timestamp")
    private LocalDateTime lastModifiedDate;
    @CreationTimestamp
    @Column(name = "creation_timestamp")
    private Instant creationTimestamp;
}
