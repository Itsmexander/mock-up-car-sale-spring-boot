package com.example.test.service;

import com.example.test.domain.Car;
import com.example.test.dto.CarInfoUpdateRequest;
import com.example.test.exception.CustomValidationException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CarService {

    void saveCar(Car car);
    List<Car> getAll();
    Optional<Car> getCarById(Long id);
    void deleteCar(long id);
    void updateCar(CarInfoUpdateRequest carInfoUpdateRequest, long id) throws CustomValidationException;
    List<Map<String, Object>> searchCars(String query);
    List<Car> getCars(int page, int size);
    long getTotalCars();
}
