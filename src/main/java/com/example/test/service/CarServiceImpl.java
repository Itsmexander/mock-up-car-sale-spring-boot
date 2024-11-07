package com.example.test.service;

import com.example.test.dao.CarDao;
import com.example.test.domain.Car;
import com.example.test.dto.CarInfoUpdateRequest;
import com.example.test.exception.CustomValidationException;
import com.example.test.validator.CarValidator;
import com.example.test.exception.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {
    private final CarDao carDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

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
    public void deleteCar(long id) {
        System.out.println("deleting car id"+id);
        carDao.deleteCar(id);
        System.out.println("deleting car id"+id+" success");
    }

    @Override
    public void updateCar(CarInfoUpdateRequest carInfoUpdateRequest, long id) throws CustomValidationException {
        Car car = new Car();
        // Map fields from carInfoUpdateRequest to car
        car.setCarId(carInfoUpdateRequest.getCarId());
        car.setName(carInfoUpdateRequest.getName());
        car.setPrice(carInfoUpdateRequest.getPrice());
        car.setNotation(carInfoUpdateRequest.getNotation());
        car.setManufacturer(carInfoUpdateRequest.getManufacturer());
        car.setManufacturedYear(Integer.parseInt(carInfoUpdateRequest.getManufacturedYear()));

        CarValidator validator = new CarValidator();
        try {
            validator.validate(car);
        } catch (ValidatorException e) {
            Logger logger = LoggerFactory.getLogger(this.getClass());
            logger.error("Validation failed: {}", e.getMessage());
            throw new CustomValidationException("Validation failed: " + e.getMessage());
        }
        System.out.println("updating on car id:"+id+" success!"
                            +"\nCar name: "+carInfoUpdateRequest.getName()
                            +"\nCar price: "+carInfoUpdateRequest.getPrice()
                            +"\nCar notation: "+carInfoUpdateRequest.getNotation()
                            +"\nCar manufacturer: "+carInfoUpdateRequest.getManufacturer()
                            +"\nManufactured Year: "+carInfoUpdateRequest.getManufacturedYear());
        carDao.updateCar(car, id);
    }

    @Override
    public List<Map<String, Object>> searchCars(String query, String sortBy, String sortOrder) {
        switch (sortBy) {
            case "carId":
            case "name":
            case "price":
            case "notation":
            case "manufacturer":
            case "manufacturedYear":
            case "timestamp":
                break;
            default:
                throw new IllegalArgumentException("Invalid sortBy parameter");
        }

        String sql = "SELECT * FROM car WHERE name LIKE ? ORDER BY " + sortBy + " " + sortOrder;
        return jdbcTemplate.queryForList(sql, "%" + query + "%");
    }
    @Override
    public List<Car> getCars(int page, int size) {
        int offset = (page) * size;
        String sql = "SELECT * FROM car LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql,new Object[]{size,offset},new CarRowMapper());
    }
    @Override
    public long getTotalCars() {
        String sql = "SELECT COUNT(*) FROM car";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }
}
