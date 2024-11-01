package com.example.test.controller;

import com.example.test.dao.CarDaoImpl;
import com.example.test.domain.Car;
import com.example.test.service.CarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping

public class CarController {

    private final CarServiceImpl carServiceImpl;
    private final CarDaoImpl carDao;

    @Autowired
    public CarController(CarServiceImpl carServiceImpl, CarDaoImpl carDao) {
        this.carServiceImpl = carServiceImpl;
        this.carDao = carDao;
    }

    @PostMapping("/registerCar")
    public void registerCar(@RequestBody Car car) {
        carServiceImpl.saveCar(car);
    }

    @GetMapping("/store")
    public List<Car> getAllCars() {
        return carServiceImpl.getAll();
    }

    @PutMapping("/updateCar/{id}")
    public void updateCar(@RequestBody Car car, @PathVariable long id ) {
        carServiceImpl.updateCar(car,id);

    }

    @DeleteMapping("/deleteCar/{id}")
    public void deleteCar(Car car,@PathVariable Long id) {
        carServiceImpl.deleteCar(car,id);
    }

    @GetMapping("/car/{id}")
    public Optional<Car> getCarById(@PathVariable Long id) {
        return carServiceImpl.getCarById(id);
    }
}