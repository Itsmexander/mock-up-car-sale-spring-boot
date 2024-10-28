package com.example.test.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@ToString
public class Car {

    private long carId;


    private String carName;

    private float price;


    private String carDesc;

    private String manufacturer;
    private int manufacturedYear;

    private Instant lastModifiedDate;
    private Instant creationTimestamp;
}
