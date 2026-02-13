package com.example.test.dao;

import com.example.test.domain.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    private CarDaoJdbcImpl carDao;

    @BeforeEach
    void setUp() {
        carDao = new CarDaoJdbcImpl(jdbcTemplate);
    }

    private Car sampleCar() {
        Car car = new Car();
        car.setCarId(1L);
        car.setName("Toyota Camry");
        car.setPrice(25000);
        car.setNotation("Test notation");
        car.setManufacturer("Toyota");
        car.setManufacturedYear(2023);
        return car;
    }

    @Test
    void getCarById_found() {
        Car car = sampleCar();
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1L)))
                .thenReturn(List.of(car));

        Optional<Car> result = carDao.getCarById(1L);

        assertTrue(result.isPresent());
        assertEquals("Toyota Camry", result.get().getName());
    }

    @Test
    void getCarById_notFound() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(999L)))
                .thenReturn(Collections.emptyList());

        Optional<Car> result = carDao.getCarById(999L);

        assertFalse(result.isPresent());
    }

    @Test
    void getAll_returnsCars() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(List.of(sampleCar()));

        List<Car> result = carDao.getAll();

        assertEquals(1, result.size());
        assertEquals("Toyota Camry", result.get(0).getName());
    }

    @Test
    void save_executesSql() {
        when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class)))
                .thenReturn(1);

        Car car = sampleCar();
        carDao.save(car);

        verify(jdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
    }

    @Test
    void updateCar_executesSql() {
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(1);

        carDao.updateCar(sampleCar(), 1L);

        verify(jdbcTemplate).update(anyString(), any(), any(), any(), any(), any(), any(), any());
    }

    @Test
    void deleteCar_executesSql() {
        when(jdbcTemplate.update(anyString(), eq(1L))).thenReturn(1);

        carDao.deleteCar(1L);

        verify(jdbcTemplate).update(anyString(), eq(1L));
    }

    @Test
    void searchCars_returnsCarList() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), any(), any(), any(),
                anyDouble(), anyDouble(), anyInt(), anyInt(), anyInt(), anyInt()))
                .thenReturn(List.of(sampleCar()));

        List<Car> result = carDao.searchCars("Toyota", "name", "asc", 0, 1000000, 1900, 2024, 0, 10);

        assertEquals(1, result.size());
        assertEquals("Toyota Camry", result.get(0).getName());
    }

    @Test
    void getCars_returnsPagedList() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyInt(), anyInt()))
                .thenReturn(List.of(sampleCar()));

        List<Car> result = carDao.getCars(0, 10);

        assertEquals(1, result.size());
    }

    @Test
    void getTotalCars_returnsCount() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Long.class))).thenReturn(5L);

        long count = carDao.getTotalCars();

        assertEquals(5L, count);
    }
}
