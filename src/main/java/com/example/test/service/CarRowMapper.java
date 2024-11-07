package com.example.test.service;

import com.example.test.domain.Car;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarRowMapper implements RowMapper<Car> {
    @Override
    public Car mapRow(ResultSet rs, int rowNum) throws SQLException {
        Car car = new Car();
        car.setCarId(rs.getInt("car_id"));
        car.setName(rs.getString("name"));
        car.setPrice(rs.getFloat("price"));
        car.setNotation(rs.getString("notation"));
        car.setManufacturer(rs.getString("manufacturer"));
        car.setManufacturedYear(rs.getInt("manufactured_year"));
        car.setCreationTimestamp(rs.getTimestamp("creation_timestamp"));
        return car;
    }
}
