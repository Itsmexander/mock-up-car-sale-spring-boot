package com.example.test.service;

import com.example.test.dto.CarInfoUpdateRequest;
import com.example.test.entities.Car;
import com.example.test.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return carRepository.findByCarId(id) ; // or throw an exception if preferred
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public void deleteCarById(Long id) {
        carRepository.deleteById(id);
    }

//    public Car updateCar(Long id,Car car) {
//        car = carRepository.findById(id).orElseThrow(()->new RuntimeException("car not found"));
//        car.setCarName(car.getCarName());
//        car.setCarDesc(car.getCarDesc());
//        car.setPrice(car.getPrice());
//        car.setManufacturer(car.getManufacturer());
//        car.setManufacturedYear(car.getManufacturedYear());
//        return carRepository.save(car);
//    }

    public void updateCar(Long id, CarInfoUpdateRequest request) {
        Car car = carRepository.findByCarId(id);
        car.setCarName(request.getCarName());
        car.setPrice(request.getPrice());
        car.setCarDesc(request.getCarDesc());
        car.setManufacturer(request.getManufacturer());
        car.setManufacturedYear(car.getManufacturedYear());
        carRepository.save(car);
    }
}
