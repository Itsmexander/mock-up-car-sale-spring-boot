package com.example.test.dao;

import com.example.test.domain.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CarDaoImpl implements CarDao {
    private Connection conn;

    @Autowired
    public CarDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Optional<Car> getCarById(long id) {
        String sql = "select *" +
                " from car where car_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
                if (rs.next()){
                    Car car = new Car();
                    car.setCarId(id);
                    car.setName(rs.getString("name"));
                    car.setPrice(rs.getInt("price"));
                    car.setNotation(rs.getString("notation"));
                    car.setManufacturer(rs.getString("manufacturer"));
                    car.setManufacturedYear(rs.getInt("manufactured_year"));
                    car.setLastModifiedTimestamp(rs.getTimestamp("last_modified_timestamp"));
                    car.setCreationTimestamp(rs.getTimestamp("creation_timestamp"));
                    return Optional.of(car);
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return Optional.empty();
    }


    @Override
    public List<Car> getAll() {
        List<Car> cars = new ArrayList<>();
        String sql = "select *"+
                " from car";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                Car car = new Car();
                car.setCarId(rs.getLong("car_id"));
                car.setName(rs.getString("name"));
                car.setPrice(rs.getInt("price"));
                car.setNotation(rs.getString("notation"));
                car.setManufacturer(rs.getString("manufacturer"));
                car.setManufacturedYear(rs.getInt("manufactured_year"));
                car.setLastModifiedTimestamp(rs.getTimestamp("last_modified_timestamp"));
                car.setCreationTimestamp(rs.getTimestamp("creation_timestamp"));
                cars.add(car);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return cars;
    }

    @Override
    public void save(Car car) {
        String sql = "insert into car (name, price, notation, manufacturer, manufactured_year, creation_timestamp) values (?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, car.getName());
            ps.setDouble(2, car.getPrice());
            ps.setString(3, car.getNotation() != null ? car.getNotation() : "");
            ps.setString(4, car.getManufacturer());
            ps.setInt(5, car.getManufacturedYear());
            ps.setTimestamp(6, new Timestamp(System.currentTimeMillis())); // Set the current timestamp

            System.out.println("Executing SQL: " + ps.toString()); // Log the SQL statement

            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    car.setCarId(generatedKeys.getLong(1));
                    car.setCreationTimestamp(new Timestamp(System.currentTimeMillis())); // Set the current timestamp
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCar(Car car, long id) {
        String sql = "UPDATE car SET name = ?, price = ?, notation = ?, manufacturer = ?, manufactured_year = ?, last_modified_timestamp = ? WHERE car_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, car.getName());
            ps.setFloat(2, car.getPrice());
            ps.setString(3, car.getNotation());
            ps.setString(4, car.getManufacturer());
            ps.setInt(5, car.getManufacturedYear());
            ps.setTimestamp(6, car.getLastModifiedTimestamp());
            ps.setLong(7, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCar(Car car, long id) {
        String sql = "delete from car where car_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setLong(1, car.getCarId());
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public Connection getConnection() {
        return this.conn;
    }
}
