package com.example.test.service;

import com.example.test.domain.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {

    void saveCar(Car car);
    List<Car> getAll();
    Optional<Car> getCarById(Long id);
    void deleteCar(Car car,Long id);
}
