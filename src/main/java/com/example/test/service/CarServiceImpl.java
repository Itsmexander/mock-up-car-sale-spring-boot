package com.example.test.service;

import com.example.test.dao.CarDao;
import com.example.test.domain.Car;
import com.example.test.dto.CarInfoUpdateRequest;
import com.example.test.validator.CarValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CarServiceImpl implements CarService {

    private final CarDao carDao;

    @Autowired
    public CarServiceImpl(CarDao carDao) {
        this.carDao = carDao;
    }

    @Override
    public void saveCar(Car car) {
        log.debug("[CarService] saveCar - name={}, price={}", car.getName(), car.getPrice());
        CarValidator validator = new CarValidator();
        validator.validate(car);
        carDao.save(car);
        log.debug("[CarService] saveCar - saved");
    }

    @Override
    public Optional<Car> getCarById(Long id) {
        log.debug("[CarService] getCarById - id={}", id);
        Optional<Car> result = carDao.getCarById(id);
        log.debug("[CarService] getCarById - id={}, result={}", id, result.isPresent() ? "found" : "empty");
        return result;
    }

    @Override
    public List<Car> getAll() {
        log.debug("[CarService] getAll");
        List<Car> cars = carDao.getAll();
        log.debug("[CarService] getAll - returned {} cars", cars.size());
        return cars;
    }

    @Override
    public void deleteCar(long id) {
        log.debug("[CarService] deleteCar - id={}", id);
        carDao.deleteCar(id);
        log.debug("[CarService] deleteCar - id={} deleted", id);
    }

    @Override
    public void updateCar(CarInfoUpdateRequest carInfoUpdateRequest, long id) {
        log.debug("[CarService] updateCar - id={}", id);
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
        log.debug("[CarService] updateCar - id={} updated", id);
    }

    @Override
    public List<Car> searchCars(String query, String sortBy, String sortOrder,
                                double minPrice, double maxPrice,
                                int minYear, int maxYear,
                                int page, int size) {
        log.debug("[CarService] searchCars - query={}, sortBy={}, sortOrder={}, minPrice={}, maxPrice={}, minYear={}, maxYear={}, page={}, size={}",
                query, sortBy, sortOrder, minPrice, maxPrice, minYear, maxYear, page, size);
        List<String> validSortColumns = Arrays.asList(
                "carId", "name", "price", "notation", "manufacturer", "manufacturedYear", "creation_timestamp");
        if (!validSortColumns.contains(sortBy)) {
            throw new IllegalArgumentException("Invalid sortBy parameter");
        }

        if (!sortOrder.equalsIgnoreCase("asc") && !sortOrder.equalsIgnoreCase("desc")) {
            throw new IllegalArgumentException("Invalid sortOrder parameter");
        }

        List<Car> results = carDao.searchCars(query, sortBy, sortOrder, minPrice, maxPrice, minYear, maxYear, page, size);
        log.debug("[CarService] searchCars - returned {} cars", results.size());
        return results;
    }

    @Override
    public List<Car> getCars(int page, int size) {
        log.debug("[CarService] getCars - page={}, size={}", page, size);
        List<Car> cars = carDao.getCars(page, size);
        log.debug("[CarService] getCars - returned {} cars", cars.size());
        return cars;
    }

    @Override
    public long getTotalCars() {
        long total = carDao.getTotalCars();
        log.debug("[CarService] getTotalCars - total={}", total);
        return total;
    }
}
