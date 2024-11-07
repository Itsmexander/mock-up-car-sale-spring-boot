package com.example.test.dao;

import com.example.test.domain.Car;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface CarDao {
    Optional<Car> getCarById(Long id);
    List<Car> getAll();
    void save(Car car);
    void updateCar(Car car, Long id);
    void deleteCar(long id);
    Connection getConnection();
}
