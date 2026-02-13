package com.example.test.dao;

import com.example.test.domain.Car;

import java.util.List;
import java.util.Optional;

public interface CarDao {
    Optional<Car> getCarById(Long id);
    List<Car> getAll();
    void save(Car car);
    void updateCar(Car car, Long id);
    void deleteCar(long id);
    List<Car> searchCars(String query, String sortBy, String sortOrder, double minPrice, double maxPrice, int minYear, int maxYear, int page, int size);
    List<Car> getCars(int page, int size);
    long getTotalCars();
}
