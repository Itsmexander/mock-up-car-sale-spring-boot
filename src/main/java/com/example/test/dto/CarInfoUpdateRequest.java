package com.example.test.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarInfoUpdateRequest {
    private long carId;
    private String name;
    private String notation;
    private float price;
    private String manufacturer;
    private String manufacturedYear;
}
