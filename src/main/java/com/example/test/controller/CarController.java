package com.example.test.controller;

import com.example.test.domain.Car;
import com.example.test.dto.CarInfoUpdateRequest;
import com.example.test.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/registerCar")
    public ResponseEntity<Car> registerCar(@RequestBody Car car) {
        carService.saveCar(car);
        return ResponseEntity.status(HttpStatus.CREATED).body(car);
    }

    @GetMapping("/store")
    public ResponseEntity<List<Car>> searchItems(@RequestParam String query,
                                                 @RequestParam(defaultValue = "name") String sortBy,
                                                 @RequestParam(defaultValue = "asc") String sortOrder,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam(defaultValue = "0") double minPrice,
                                                 @RequestParam(defaultValue = "1000000") double maxPrice,
                                                 @RequestParam(defaultValue = "1900") int minYear,
                                                 @RequestParam(defaultValue = "2024") int maxYear) {
        List<Car> cars = carService.searchCars(query, sortBy, sortOrder, minPrice, maxPrice, minYear, maxYear, page, size);
        long total = carService.getTotalCars();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(total));
        return new ResponseEntity<>(cars, headers, HttpStatus.OK);
    }

    @PutMapping("/update-car/{id}")
    public ResponseEntity<Void> updateCar(@RequestBody CarInfoUpdateRequest car, @PathVariable long id) {
        carService.updateCar(car, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteCar/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/car/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable long id) {
        Optional<Car> car = carService.getCarById(id);
        return car.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
