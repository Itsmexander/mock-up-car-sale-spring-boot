package com.example.test.controller;

import com.example.test.domain.Car;
import com.example.test.dto.CarInfoUpdateRequest;
import com.example.test.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
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
        log.info("[CarController] POST /registerCar - car={}, price={}", car.getName(), car.getPrice());
        carService.saveCar(car);
        log.info("[CarController] POST /registerCar - 201 CREATED, carId={}", car.getCarId());
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
        log.info("[CarController] GET /store - query={}, sortBy={}, sortOrder={}, page={}, size={}, minPrice={}, maxPrice={}, minYear={}, maxYear={}",
                query, sortBy, sortOrder, page, size, minPrice, maxPrice, minYear, maxYear);
        List<Car> cars = carService.searchCars(query, sortBy, sortOrder, minPrice, maxPrice, minYear, maxYear, page, size);
        long total = carService.getTotalCars();
        log.info("[CarController] GET /store - 200 OK, returned {} cars, totalCount={}", cars.size(), total);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(total));
        return new ResponseEntity<>(cars, headers, HttpStatus.OK);
    }

    @PutMapping("/update-car/{id}")
    public ResponseEntity<Void> updateCar(@RequestBody CarInfoUpdateRequest car, @PathVariable long id) {
        log.info("[CarController] PUT /update-car/{} - body={}", id, car);
        carService.updateCar(car, id);
        log.info("[CarController] PUT /update-car/{} - 200 OK", id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteCar/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable long id) {
        log.info("[CarController] DELETE /deleteCar/{}", id);
        carService.deleteCar(id);
        log.info("[CarController] DELETE /deleteCar/{} - 204 NO CONTENT", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/car/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable long id) {
        log.info("[CarController] GET /car/{}", id);
        Optional<Car> car = carService.getCarById(id);
        log.info("[CarController] GET /car/{} - {}", id, car.isPresent() ? "FOUND" : "NOT FOUND");
        return car.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
