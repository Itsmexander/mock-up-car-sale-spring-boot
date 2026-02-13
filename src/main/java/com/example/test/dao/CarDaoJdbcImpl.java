package com.example.test.dao;

import com.example.test.domain.Car;
import com.example.test.service.CarRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("jdbc")
public class CarDaoJdbcImpl implements CarDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CarDaoJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Car> getCarById(Long id) {
        String sql = "SELECT * FROM car WHERE carId = ?";
        List<Car> cars = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Car car = new Car();
            car.setCarId(rs.getLong("carId"));
            car.setName(rs.getString("name"));
            car.setPrice(rs.getFloat("price"));
            car.setNotation(rs.getString("notation"));
            car.setManufacturer(rs.getString("manufacturer"));
            car.setManufacturedYear(rs.getInt("manufacturedYear"));
            car.setLastModifiedTimestamp(rs.getTimestamp("last_modified_timestamp"));
            car.setCreationTimestamp(rs.getTimestamp("creation_timestamp"));
            return car;
        }, id);
        return cars.isEmpty() ? Optional.empty() : Optional.of(cars.get(0));
    }

    @Override
    public List<Car> getAll() {
        String sql = "SELECT * FROM car";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Car car = new Car();
            car.setCarId(rs.getLong("carId"));
            car.setName(rs.getString("name"));
            car.setPrice(rs.getFloat("price"));
            car.setNotation(rs.getString("notation"));
            car.setManufacturer(rs.getString("manufacturer"));
            car.setManufacturedYear(rs.getInt("manufacturedYear"));
            car.setLastModifiedTimestamp(rs.getTimestamp("last_modified_timestamp"));
            car.setCreationTimestamp(rs.getTimestamp("creation_timestamp"));
            return car;
        });
    }

    @Override
    public void save(Car car) {
        String sql = "INSERT INTO car (name, price, notation, manufacturer, manufacturedYear, creation_timestamp) VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"carId"});
            ps.setString(1, car.getName());
            ps.setDouble(2, car.getPrice());
            ps.setString(3, car.getNotation() != null ? car.getNotation() : "");
            ps.setString(4, car.getManufacturer());
            ps.setInt(5, car.getManufacturedYear());
            ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            car.setCarId(keyHolder.getKey().longValue());
            car.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));
        }
    }

    @Override
    public void updateCar(Car car, Long id) {
        String sql = "UPDATE car SET name = ?, price = ?, notation = ?, manufacturer = ?, manufacturedYear = ?, last_modified_timestamp = ? WHERE carId = ?";
        jdbcTemplate.update(sql,
                car.getName(),
                car.getPrice(),
                car.getNotation(),
                car.getManufacturer(),
                car.getManufacturedYear(),
                car.getLastModifiedTimestamp(),
                id);
    }

    @Override
    public void deleteCar(long id) {
        String sql = "DELETE FROM car WHERE carId = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Car> searchCars(String query, String sortBy, String sortOrder,
                                double minPrice, double maxPrice,
                                int minYear, int maxYear,
                                int page, int size) {
        int offset = page * size;
        String sql = "SELECT * FROM car WHERE (name LIKE ? OR notation LIKE ? OR manufacturer LIKE ?) "
                + "AND price BETWEEN ? AND ? "
                + "AND manufacturedYear BETWEEN ? AND ? "
                + "ORDER BY " + sortBy + " " + sortOrder
                + " LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new CarRowMapper(),
                "%" + query + "%", "%" + query + "%", "%" + query + "%",
                minPrice, maxPrice,
                minYear, maxYear,
                size, offset);
    }

    @Override
    public List<Car> getCars(int page, int size) {
        int offset = page * size;
        String sql = "SELECT * FROM car LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new CarRowMapper(), size, offset);
    }

    @Override
    public long getTotalCars() {
        String sql = "SELECT COUNT(*) FROM car";
        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        return count != null ? count : 0L;
    }
}
