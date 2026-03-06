package com.example.test.service;

import com.example.test.dao.CarDao;
import com.example.test.domain.Car;
import com.example.test.dto.CarInfoUpdateRequest;
import com.example.test.exception.ValidatorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @Mock
    private CarDao carDao;

    private CarServiceImpl carService;

    @BeforeEach
    void setUp() {
        carService = new CarServiceImpl(carDao);
    }

    private Car validCar() {
        Car car = new Car();
        car.setCarId(1L);
        car.setName("Toyota Camry");
        car.setPrice(25000);
        car.setNotation("Test");
        car.setManufacturer("Toyota");
        car.setManufacturedYear(2023);
        return car;
    }

    @Test
    void saveCar_valid() {
        Car car = validCar();
        carService.saveCar(car);
        verify(carDao).save(car);
    }

    @Test
    void saveCar_invalid_throwsException() {
        Car car = new Car();
        car.setName("");
        car.setPrice(0);
        assertThrows(ValidatorException.class, () -> carService.saveCar(car));
        verify(carDao, never()).save(any());
    }

    @Test
    void getCarById_found() {
        when(carDao.getCarById(1L)).thenReturn(Optional.of(validCar()));
        Optional<Car> result = carService.getCarById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void getCarById_notFound() {
        when(carDao.getCarById(999L)).thenReturn(Optional.empty());
        Optional<Car> result = carService.getCarById(999L);
        assertFalse(result.isPresent());
    }

    @Test
    void getAll() {
        when(carDao.getAll()).thenReturn(List.of(validCar()));
        assertEquals(1, carService.getAll().size());
    }

    @Test
    void deleteCar() {
        carService.deleteCar(1L);
        verify(carDao).deleteCar(1L);
    }

    @Test
    void updateCar_valid() {
        CarInfoUpdateRequest request = new CarInfoUpdateRequest();
        request.setCarId(1L);
        request.setName("Updated");
        request.setPrice(30000);
        request.setNotation("Updated notation");
        request.setManufacturer("Toyota");
        request.setManufacturedYear("2023");

        carService.updateCar(request, 1L);
        verify(carDao).updateCar(any(Car.class), eq(1L));
    }

    @Test
    void updateCar_invalid_throwsException() {
        CarInfoUpdateRequest request = new CarInfoUpdateRequest();
        request.setName("");
        request.setPrice(0);
        request.setManufacturedYear("2023");

        assertThrows(ValidatorException.class, () -> carService.updateCar(request, 1L));
        verify(carDao, never()).updateCar(any(), anyLong());
    }

    @Test
    void searchCars_validSort() {
        List<Car> expected = List.of(validCar());
        when(carDao.searchCars(eq("Toyota"), eq("name"), eq("asc"),
                eq(0.0), eq(1000000.0), eq(1900), eq(2024), eq(0), eq(10)))
                .thenReturn(expected);

        List<Car> result = carService.searchCars("Toyota", "name", "asc", 0, 1000000, 1900, 2024, 0, 10);
        assertEquals(1, result.size());
    }

    @Test
    void searchCars_invalidSortBy() {
        assertThrows(IllegalArgumentException.class,
                () -> carService.searchCars("q", "invalid_column", "asc", 0, 100000, 1900, 2024, 0, 10));
    }

    @Test
    void searchCars_invalidSortOrder() {
        assertThrows(IllegalArgumentException.class,
                () -> carService.searchCars("q", "name", "invalid", 0, 100000, 1900, 2024, 0, 10));
    }

    @Test
    void searchCars_descSortOrder() {
        when(carDao.searchCars(any(), any(), eq("desc"), anyDouble(), anyDouble(), anyInt(), anyInt(), anyInt(), anyInt()))
                .thenReturn(List.of(validCar()));

        List<Car> result = carService.searchCars("Toyota", "name", "desc", 0, 1000000, 1900, 2024, 0, 10);
        assertEquals(1, result.size());
    }

    @Test
    void getCars() {
        when(carDao.getCars(0, 10)).thenReturn(List.of(validCar()));

        List<Car> result = carService.getCars(0, 10);
        assertEquals(1, result.size());
    }

    @Test
    void getTotalCars() {
        when(carDao.getTotalCars()).thenReturn(5L);
        assertEquals(5L, carService.getTotalCars());
    }
}
