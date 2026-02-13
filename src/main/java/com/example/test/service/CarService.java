package com.example.test.service;

import com.example.test.domain.Car;
import com.example.test.dto.CarInfoUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface CarService {

    void saveCar(Car car);
    List<Car> getAll();
    Optional<Car> getCarById(Long id);
    void deleteCar(long id);
    void updateCar(CarInfoUpdateRequest carInfoUpdateRequest, long id);
    List<Car> searchCars(String query, String sortBy, String sortOrder, double minPrice, double maxPrice, int minYear, int maxYear, int page, int size);
    List<Car> getCars(int page, int size);
    long getTotalCars();
}
