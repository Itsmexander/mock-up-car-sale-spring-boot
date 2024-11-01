package com.example.test.service;

import com.example.test.dao.CarDao;
import com.example.test.domain.Car;
import com.example.test.validator.CarValidator;
import com.example.test.validator.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {
    private final CarDao carDao;

    @Autowired
    public CarServiceImpl(CarDao carDao) {
        this.carDao = carDao;
    }

    @Override
    public void saveCar(Car car) {
        CarValidator validator = new CarValidator();
        try{
            validator.validate(car);
        } catch (ValidatorException e) {
            System.err.println("Validation failed:"+e.getMessage());
            return;
        }
        carDao.save(car);
    }

    @Override
    public Optional<Car> getCarById(Long id) {
        return carDao.getCarById(id) ;
    }

    @Override
    public List<Car> getAll() {
        return carDao.getAll();
    }

    @Override
    public void deleteCar(Car car,Long id) {
        carDao.deleteCar(car, id);
    }

    public void updateCar(Car car,Long id) {
        CarValidator validator = new CarValidator();
        try{
            validator.validate(car);
        } catch (ValidatorException e) {
            System.err.println("Validation failed:"+e.getMessage());
            return;
        }
        carDao.updateCar(car,id);
    }
}
