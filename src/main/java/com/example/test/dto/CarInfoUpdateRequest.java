package com.example.test.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarInfoUpdateRequest {
    private long carId;
    private String carName;
    private String carDesc;
    private float price;
    private String manufacturer;
    private String manufacturedYear;
}
