package com.example.test.dao;

import com.example.test.domain.Car;
import com.example.test.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("jpa")
public class CarDaoJpaImpl implements CarDao {

    private final CarRepository carRepository;

    @Autowired
    public CarDaoJpaImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    @Override
    public List<Car> getAll() {
        return carRepository.findAll();
    }

    @Override
    public void save(Car car) {
        carRepository.save(car);
    }

    @Override
    public void updateCar(Car car, Long id) {
        car.setCarId(id);
        carRepository.save(car);
    }

    @Override
    public void deleteCar(long id) {
        carRepository.deleteById(id);
    }

    @Override
    public List<Car> searchCars(String query, String sortBy, String sortOrder,
                                double minPrice, double maxPrice,
                                int minYear, int maxYear,
                                int page, int size) {
        Sort sort = sortOrder.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Car> result = carRepository.searchCars(query, minPrice, maxPrice, minYear, maxYear, pageable);
        return result.getContent();
    }

    @Override
    public List<Car> getCars(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return carRepository.findAll(pageable).getContent();
    }

    @Override
    public long getTotalCars() {
        return carRepository.count();
    }
}
