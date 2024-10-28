package com.example.test.dao;

import com.example.test.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private Connection conn;

    public UserDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Optional<User> getUserById(long id) {
        String sql = "select *" +
                " from user where id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setFirstname(rs.getString("firstname"));
                user.setSurname(rs.getString("surname"));
                user.setAddress(rs.getString("address"));
                user.setTelNO(rs.getString("tel_no"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setCreationTimestamp(rs.getTimestamp("creation_timestamp"));
                user.setLastModifiedTimestamp(rs.getTimestamp("last_modified_timestamp"));
                return Optional.of(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "select * " +
                "from user";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setFirstname(rs.getString("first_name"));
                user.setSurname(rs.getString("surname"));
                user.setAddress(rs.getString("address"));
                user.setTelNO(rs.getString("tel_no"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setCreationTimestamp(rs.getTimestamp("creation_timestamp"));
                user.setLastModifiedTimestamp(rs.getTimestamp("last_modified_timestamp"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void saveUser(User user) {
        String sql = "insert into user " +
                "(firstname, surname, address, tel_no, email, password, creation_timestamp) " +
                "values (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)){
            preparedStatement.setString(1, user.getFirstname());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getAddress());
            preparedStatement.setString(4, user.getTelNO());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                    user.setCreationTimestamp(generatedKeys.getTimestamp(7));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(User user) {
        String sql = "update into user (firstname, surname, address, tel_no, email, password, last_modified_timestamp) values (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)){
            preparedStatement.setString(1, user.getFirstname());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getAddress());
            preparedStatement.setString(4, user.getTelNO());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                    user.setLastModifiedTimestamp(generatedKeys.getTimestamp(7));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteUser(User user) {
        String sql = "delete from user where id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setLong(1, user.getId());
            pstmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
