package com.example.test.controller;

import com.example.test.dao.CarDaoImpl;
import com.example.test.domain.Car;
import com.example.test.dto.CarInfoUpdateRequest;
import com.example.test.exception.CustomValidationException;
import com.example.test.service.CarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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

//    @GetMapping("/store")
//    public List<Car> getAllCars() {
//        return carServiceImpl.getAll();
//    }

    @GetMapping("/store")
    public ResponseEntity<List<Map<String, Object>>> searchItems(@RequestParam String query,
                                                                 @RequestParam(defaultValue = "name") String sortBy,
                                                                 @RequestParam(defaultValue = "asc") String sortOrder,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size,
                                                                 @RequestParam(defaultValue = "0") double minPrice,
                                                                 @RequestParam(defaultValue = "1000000") double maxPrice,
                                                                 @RequestParam(defaultValue = "1900") int minYear,
                                                                 @RequestParam(defaultValue = "2024") int maxYear) {
        List<Map<String, Object>> cars = carServiceImpl.searchCars(query, sortBy, sortOrder, minPrice, maxPrice, minYear, maxYear, page, size);
        long total = carServiceImpl.getTotalCars();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(total));
        return new ResponseEntity<>(cars, headers, HttpStatus.OK);
    }

    @PutMapping("/update-car/{id}")
    public void updateCar(@RequestBody CarInfoUpdateRequest car, @PathVariable long id ) throws CustomValidationException {
        System.out.println("updating car id:"+id);
        carServiceImpl.updateCar(car,id);

    }

    @DeleteMapping("/deleteCar/{id}")
    public void deleteCar(@PathVariable long id) {
        System.out.println("delete car id:"+id);
        carServiceImpl.deleteCar(id);
    }
//
//    @GetMapping("/car/search")
//    public List<Map<String, Object>> searchItems(@RequestParam String query,@RequestParam(defaultValue = "name") String sortBy,@RequestParam(defaultValue = "asc") String sortOrder) {
//        return carServiceImpl.searchCars(query, sortBy, sortOrder);
//    }

    @GetMapping("/car/{id}")
    public Optional<Car> getCarById(@PathVariable long id) {
        return carServiceImpl.getCarById(id);
    }
}