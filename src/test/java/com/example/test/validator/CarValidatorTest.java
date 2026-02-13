package com.example.test.validator;

import com.example.test.domain.Car;
import com.example.test.exception.ValidatorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarValidatorTest {

    private CarValidator validator;

    @BeforeEach
    void setUp() {
        validator = new CarValidator();
    }

    private Car validCar() {
        Car car = new Car();
        car.setName("Toyota Camry");
        car.setPrice(25000);
        car.setManufacturedYear(2023);
        car.setManufacturer("Toyota");
        return car;
    }

    @Test
    void shouldPassValidation_whenCarIsValid() {
        assertDoesNotThrow(() -> validator.validate(validCar()));
    }

    @Test
    void shouldThrow_whenNameIsNull() {
        Car car = validCar();
        car.setName(null);
        ValidatorException ex = assertThrows(ValidatorException.class, () -> validator.validate(car));
        assertEquals("Car name cannot be empty", ex.getMessage());
    }

    @Test
    void shouldThrow_whenNameIsEmpty() {
        Car car = validCar();
        car.setName("");
        ValidatorException ex = assertThrows(ValidatorException.class, () -> validator.validate(car));
        assertEquals("Car name cannot be empty", ex.getMessage());
    }

    @Test
    void shouldThrow_whenPriceIsZero() {
        Car car = validCar();
        car.setPrice(0);
        ValidatorException ex = assertThrows(ValidatorException.class, () -> validator.validate(car));
        assertEquals("Car price must be positive", ex.getMessage());
    }

    @Test
    void shouldThrow_whenPriceIsNegative() {
        Car car = validCar();
        car.setPrice(-100);
        ValidatorException ex = assertThrows(ValidatorException.class, () -> validator.validate(car));
        assertEquals("Car price must be positive", ex.getMessage());
    }

    @Test
    void shouldThrow_whenYearTooOld() {
        Car car = validCar();
        car.setManufacturedYear(1900);
        ValidatorException ex = assertThrows(ValidatorException.class, () -> validator.validate(car));
        assertEquals("Manufactured year must be between 1886 and the current year", ex.getMessage());
    }

    @Test
    void shouldThrow_whenYearInFuture() {
        Car car = validCar();
        car.setManufacturedYear(java.time.Year.now().getValue() + 1);
        ValidatorException ex = assertThrows(ValidatorException.class, () -> validator.validate(car));
        assertEquals("Manufactured year must be between 1886 and the current year", ex.getMessage());
    }
}
