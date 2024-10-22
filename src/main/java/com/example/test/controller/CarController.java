package com.example.test.controller;

import com.example.test.entities.Car;
import com.example.test.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/car")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/car")
    public Car createCar(@RequestBody Car car) {
        return carService.saveCar(car);
    }

    @GetMapping("/car/{id}")
    public Car getCar(@PathVariable Long id) {
        return carService.getCarById(id);
    }

}
