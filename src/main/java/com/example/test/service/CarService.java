package com.example.test.service;

import com.example.test.dao.CarDao;
import com.example.test.domain.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private final CarDao carDao;

    @Autowired
    public CarService(CarDao carDao) {
        this.carDao = carDao;
    }

    public void saveCar(Car car) {
        carDao.save(car);
    }

    public Optional<Car> getCarById(Long id) {
        return carDao.getCarById(id) ; // or throw an exception if preferred
    }

    public List<Car> getAll() {
        return carDao.getAll();
    }

    public void deleteCar(Car car,long id) {
        carDao.deleteCar(car, id);
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

    public void updateCar(Car car,Long id) {
        carDao.updateCar(car,id);
    }
}
