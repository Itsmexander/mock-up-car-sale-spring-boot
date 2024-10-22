package com.example.test.service;

import com.example.test.entities.Car;
import com.example.test.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarService {
    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    public Car getCarById(Long id) {
        Optional<Car> car = carRepository.findByCarId(id);
        return car.orElse(null); // or throw an exception if preferred
    }


}
