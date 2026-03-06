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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
    void searchCars_ascendingOrder() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), any(), any(), any(),
                anyDouble(), anyDouble(), anyInt(), anyInt(), anyInt(), anyInt()))
                .thenReturn(List.of(sampleCar()));

        List<Car> result = carDao.searchCars("Toyota", "name", "asc", 0, 1000000, 1900, 2024, 0, 10);

        assertEquals(1, result.size());
        assertEquals("Toyota Camry", result.get(0).getName());
    }

    @Test
    void searchCars_descendingOrder() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), any(), any(), any(),
                anyDouble(), anyDouble(), anyInt(), anyInt(), anyInt(), anyInt()))
                .thenReturn(List.of(sampleCar()));

        List<Car> result = carDao.searchCars("Toyota", "name", "desc", 0, 1000000, 1900, 2024, 0, 10);

        assertEquals(1, result.size());
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

    @Test
    void getTotalCars_returnsZero_whenCountNull() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Long.class))).thenReturn(null);

        assertEquals(0L, carDao.getTotalCars());
    }

    @Test
    void getAll_rowMapperMapsFieldsCorrectly() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.getLong("carId")).thenReturn(1L);
        when(rs.getString("name")).thenReturn("Toyota Camry");
        when(rs.getFloat("price")).thenReturn(25000f);
        when(rs.getString("notation")).thenReturn("Test notation");
        when(rs.getString("manufacturer")).thenReturn("Toyota");
        when(rs.getInt("manufacturedYear")).thenReturn(2023);
        when(rs.getTimestamp("last_modified_timestamp")).thenReturn(null);
        when(rs.getTimestamp("creation_timestamp")).thenReturn(null);

        doAnswer(invocation -> {
            RowMapper<Car> mapper = invocation.getArgument(1);
            return List.of(mapper.mapRow(rs, 0));
        }).when(jdbcTemplate).query(anyString(), any(RowMapper.class));

        List<Car> result = carDao.getAll();

        assertEquals(1, result.size());
        assertEquals("Toyota Camry", result.get(0).getName());
        assertEquals(25000f, result.get(0).getPrice());
    }

    @Test
    void save_withNullNotation_usesEmptyString() throws Exception {
        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        when(conn.prepareStatement(anyString(), any(String[].class))).thenReturn(ps);

        doAnswer(invocation -> {
            PreparedStatementCreator psc = invocation.getArgument(0);
            psc.createPreparedStatement(conn);
            return 1;
        }).when(jdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));

        Car car = sampleCar();
        car.setNotation(null);
        carDao.save(car);

        verify(ps).setString(eq(3), eq(""));
    }

    @Test
    void save_withKeyReturned_setsCarId() {
        doAnswer(invocation -> {
            KeyHolder kh = invocation.getArgument(1);
            Map<String, Object> keys = new LinkedHashMap<>();
            keys.put("carId", 42L);
            kh.getKeyList().add(keys);
            return 1;
        }).when(jdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));

        Car car = sampleCar();
        car.setCarId(0L);
        carDao.save(car);

        assertEquals(42L, car.getCarId());
    }
}
