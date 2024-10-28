package com.example.test.dao;

import com.example.test.domain.Car;

import java.util.List;
import java.util.Optional;

public interface CarDAO {
    Optional<Car> findById(long id);
    List<Car> findAll();
    void save(Car car);
    void update(Car car);
    void delete(Car car);
}
