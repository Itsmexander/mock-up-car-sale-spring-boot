package com.example.test.validator;

import com.example.test.domain.Car;

public class CarValidator  implements Validator<Car> {
    @Override
    public void validate(Car car) throws ValidatorException {
        if (car.getName()==null||car.getName().isEmpty()) {
            throw new ValidatorException("Car name cannot be empty");
        }
        if (car.getPrice() <= 0) {
            throw new ValidatorException("Car price must be positive");
        }
        if (car.getManufacturedYear() < 1920 || car.getManufacturedYear() > java.time.Year.now().getValue()) {
            throw new ValidatorException("Manufactured year must be between 1886 and the current year");
        }
    }
}
