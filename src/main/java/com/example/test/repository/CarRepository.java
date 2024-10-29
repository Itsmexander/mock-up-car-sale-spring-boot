//package com.example.test.repository;
//
//import com.example.test.entities.Car;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.Optional;
//
//public interface CarRepository extends JpaRepository<Car,Long> {
//    Car findByCarId(Long carId);
//    Car findByManufacturedYearBetween(Integer start,Integer end);
//    Car findByPriceBetween(Float minPrice,Float maxPrice);
//    Car findByPriceBetweenAndManufacturedYearBetween(Float minPrice,Float maxPrice,Integer start,Integer end);
//}
