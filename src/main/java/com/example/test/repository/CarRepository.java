package com.example.test.repository;

import com.example.test.domain.Car;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("SELECT c FROM Car c WHERE "
            + "(c.name LIKE %:query% OR c.notation LIKE %:query% OR c.manufacturer LIKE %:query%) "
            + "AND c.price BETWEEN :minPrice AND :maxPrice "
            + "AND c.manufacturedYear BETWEEN :minYear AND :maxYear")
    Page<Car> searchCars(
            @Param("query") String query,
            @Param("minPrice") double minPrice,
            @Param("maxPrice") double maxPrice,
            @Param("minYear") int minYear,
            @Param("maxYear") int maxYear,
            Pageable pageable);
}
