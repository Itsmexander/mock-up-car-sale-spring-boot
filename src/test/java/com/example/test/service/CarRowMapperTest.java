package com.example.test.service;

import com.example.test.domain.Car;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarRowMapperTest {

    @Mock
    private ResultSet rs;

    @Test
    void mapRow_mapsAllFields() throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        when(rs.getInt("carId")).thenReturn(1);
        when(rs.getString("name")).thenReturn("Toyota Camry");
        when(rs.getFloat("price")).thenReturn(25000f);
        when(rs.getString("notation")).thenReturn("Test notation");
        when(rs.getString("manufacturer")).thenReturn("Toyota");
        when(rs.getInt("manufacturedYear")).thenReturn(2023);
        when(rs.getTimestamp("creation_timestamp")).thenReturn(now);

        CarRowMapper mapper = new CarRowMapper();
        Car car = mapper.mapRow(rs, 0);

        assertNotNull(car);
        assertEquals(1, car.getCarId());
        assertEquals("Toyota Camry", car.getName());
        assertEquals(25000f, car.getPrice());
        assertEquals("Test notation", car.getNotation());
        assertEquals("Toyota", car.getManufacturer());
        assertEquals(2023, car.getManufacturedYear());
        assertEquals(now, car.getCreationTimestamp());
    }
}
