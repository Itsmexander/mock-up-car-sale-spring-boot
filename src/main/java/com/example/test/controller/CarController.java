package com.example.test.controller;


import com.example.test.dao.CarDao;
import com.example.test.dao.CarDaoImpl;
import com.example.test.domain.Car;
import com.example.test.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping

public class CarController {

    private final CarService carService;
    private final CarDaoImpl carDao;

    @Autowired
    public CarController(CarService carService, CarDaoImpl carDao) {
        this.carService = carService;
        this.carDao = carDao;
    }

    @PostMapping("/cars/registerCar")
    public void registerCar(@RequestBody Car car) {
        carService.saveCar(car);
    }

    @GetMapping("/store")
    public List<Car> getAllCars() {
        return carService.getAll();
    }

    @PutMapping("/updateCar/{id}")
    public void updateCar(@RequestBody Car car, @PathVariable long id ) {
        carService.updateCar(car,id);

    }

    @DeleteMapping("/deleteCar/{id}")
    public void deleteCar(Car car,@PathVariable Long id) {
        carService.deleteCar(car,id);
    }

    @GetMapping("/car/{id}")
    public Optional<Car> getCarById(@PathVariable Long id) {
        return carService.getCarById(id);
    }
}