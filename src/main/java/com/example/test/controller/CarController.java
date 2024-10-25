package com.example.test.controller;


import com.example.test.dto.CarInfoUpdateRequest;
import com.example.test.entities.Car;
import com.example.test.repository.CarRepository;
import com.example.test.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping

public class CarController {

    private final CarService carService;
    private final CarRepository carRepository;

    @Autowired
    public CarController(CarService carService, CarRepository carRepository) {
        this.carService = carService;
        this.carRepository = carRepository;
    }

    @PostMapping("/cars/registerCar")
    public Car registerCar(@RequestBody Car car) {
        return carService.saveCar(car);
    }

    @GetMapping("/store")
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @PutMapping("/updateCar/{id}")
    public ResponseEntity<?> updateCar(@PathVariable Long id, @RequestBody CarInfoUpdateRequest carInfoUpdateRequest) {
        carService.updateCar(id, carInfoUpdateRequest);
        return ResponseEntity.ok("car info updated");
    }

    @DeleteMapping("/deleteCar/{id}")
    public void deleteCar(@PathVariable Long id) {
        carService.deleteCarById(id);
    }

    @GetMapping("/car/{id}")
    public Car getCarById(@PathVariable Long id) {
        return carService.getCarById(id);
    }
}