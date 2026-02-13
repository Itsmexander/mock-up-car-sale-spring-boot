package com.example.test.service;

import com.example.test.dao.CarDao;
import com.example.test.domain.Car;
import com.example.test.dto.CarInfoUpdateRequest;
import com.example.test.validator.CarValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
        validator.validate(car);
        carDao.save(car);
    }

    @Override
    public Optional<Car> getCarById(Long id) {
        return carDao.getCarById(id);
    }

    @Override
    public List<Car> getAll() {
        return carDao.getAll();
    }

    @Override
    public void deleteCar(long id) {
        carDao.deleteCar(id);
    }

    @Override
    public void updateCar(CarInfoUpdateRequest carInfoUpdateRequest, long id) {
        Car car = new Car();
        car.setCarId(carInfoUpdateRequest.getCarId());
        car.setName(carInfoUpdateRequest.getName());
        car.setPrice(carInfoUpdateRequest.getPrice());
        car.setNotation(carInfoUpdateRequest.getNotation());
        car.setManufacturer(carInfoUpdateRequest.getManufacturer());
        car.setManufacturedYear(Integer.parseInt(carInfoUpdateRequest.getManufacturedYear()));

        CarValidator validator = new CarValidator();
        validator.validate(car);

        carDao.updateCar(car, id);
    }

    @Override
    public List<Car> searchCars(String query, String sortBy, String sortOrder,
                                double minPrice, double maxPrice,
                                int minYear, int maxYear,
                                int page, int size) {
        List<String> validSortColumns = Arrays.asList(
                "carId", "name", "price", "notation", "manufacturer", "manufacturedYear", "creation_timestamp");
        if (!validSortColumns.contains(sortBy)) {
            throw new IllegalArgumentException("Invalid sortBy parameter");
        }

        if (!sortOrder.equalsIgnoreCase("asc") && !sortOrder.equalsIgnoreCase("desc")) {
            throw new IllegalArgumentException("Invalid sortOrder parameter");
        }

        return carDao.searchCars(query, sortBy, sortOrder, minPrice, maxPrice, minYear, maxYear, page, size);
    }

    @Override
    public List<Car> getCars(int page, int size) {
        return carDao.getCars(page, size);
    }

    @Override
    public long getTotalCars() {
        return carDao.getTotalCars();
    }
}
