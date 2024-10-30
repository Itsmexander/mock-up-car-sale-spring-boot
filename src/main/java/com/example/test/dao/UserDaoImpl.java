package com.example.test.dao;

import com.example.test.domain.User;
import com.example.test.dto.PasswordChangeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {
    private Connection conn;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @Autowired
    public UserDaoImpl(Connection conn) {
        this.conn = conn;
    }
    @Override
    public Connection getConnection() {
        return this.conn;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        String sql = "select *" +
                " from user where email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setFirstname(rs.getString("firstname"));
                user.setSurname(rs.getString("surname"));
                user.setAddress(rs.getString("address"));
                user.setTelno(rs.getString("telno"));
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
                user.setFirstname(rs.getString("firstname"));
                user.setSurname(rs.getString("surname"));
                user.setAddress(rs.getString("address"));
                user.setTelno(rs.getString("telno"));
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
        String sql = "INSERT INTO user (firstname, surname, address, telno, email, password, creation_timestamp) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getFirstname());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getAddress());
            preparedStatement.setString(4, user.getTelno());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                    // You don't need to set creationTimestamp here as it's already set during the insert
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updateUser(User user) {
        String sql = "UPDATE user SET firstname = ?, surname = ?, address = ?, telno = ?, email = ?, password = ?, last_modified_timestamp = ? WHERE user_id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getFirstname());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getAddress());
            preparedStatement.setString(4, user.getTelno());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setLong(8, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(User user) {
        String sql = "delete from user where id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setLong(1, user.getId());
            ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void changePassword(PasswordChangeRequest request) {
        String selectSql = "SELECT * FROM user WHERE id = ?";
        String updateSql = "UPDATE user SET password = ?, last_modified_timestamp = ? WHERE id = ?";

        try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
            selectStmt.setLong(1, request.getId());
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                String currentPassword = rs.getString("password");
                if (passwordEncoder.matches(request.getOldPassword(), currentPassword)) {
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setString(1, passwordEncoder.encode(request.getNewPassword()));
                        updateStmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                        updateStmt.setLong(3, request.getId());
                        updateStmt.executeUpdate();
                    }
                } else {
                    throw new RuntimeException("Old password is incorrect");
                }
            } else {
                throw new RuntimeException("User not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error changing password", e);
        }
    }
}
