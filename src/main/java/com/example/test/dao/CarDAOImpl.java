package com.example.test.dao;

import com.example.test.domain.Car;

import java.util.List;
import java.util.Optional;

public class CarDAOImpl implements CarDAO {

    @Override
    public Optional<Car> findById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Car> findAll() {
        return List.of();
    }

    @Override
    public void save(Car car) {

    }

    @Override
    public void update(Car car) {

    }

    @Override
    public void delete(Car car) {

    }
}
