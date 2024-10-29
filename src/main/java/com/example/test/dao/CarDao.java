package com.example.test.dao;

import com.example.test.domain.Car;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface CarDao {
    Optional<Car> getCarById(long id);
    List<Car> getAll();
    void save(Car car);
    void updateCar(Car car, long id);
    void deleteCar(Car car, long id);
    Connection getConnection();
}
