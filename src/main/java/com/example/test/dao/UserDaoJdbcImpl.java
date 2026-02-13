package com.example.test.dao;

import com.example.test.domain.User;
import com.example.test.dto.PasswordChangeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("jdbc")
public class UserDaoJdbcImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserDaoJdbcImpl(JdbcTemplate jdbcTemplate, BCryptPasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        String sql = "SELECT * FROM `user` WHERE email = ?";
        List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> {
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
            return user;
        }, email);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM `user`";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
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
            return user;
        });
    }

    @Override
    public void saveUser(User user) {
        String sql = "INSERT INTO `user` (firstname, surname, address, telno, email, password, creation_timestamp) VALUES (?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getSurname());
            ps.setString(3, user.getAddress());
            ps.setString(4, user.getTelno());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getPassword());
            ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            user.setId(keyHolder.getKey().longValue());
        }
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE `user` SET firstname = ?, surname = ?, address = ?, telno = ?, email = ?, password = ?, last_modified_timestamp = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                user.getFirstname(),
                user.getSurname(),
                user.getAddress(),
                user.getTelno(),
                user.getEmail(),
                user.getPassword(),
                new Timestamp(System.currentTimeMillis()),
                user.getId());
    }

    @Override
    public void deleteUser(User user) {
        String sql = "DELETE FROM `user` WHERE id = ?";
        jdbcTemplate.update(sql, user.getId());
    }

    @Override
    public void changePassword(PasswordChangeRequest request) {
        String selectSql = "SELECT * FROM `user` WHERE id = ?";
        List<User> users = jdbcTemplate.query(selectSql, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setPassword(rs.getString("password"));
            return user;
        }, request.getId());

        if (users.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = users.get(0);
        if (passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            String updateSql = "UPDATE `user` SET password = ?, last_modified_timestamp = ? WHERE id = ?";
            jdbcTemplate.update(updateSql,
                    passwordEncoder.encode(request.getNewPassword()),
                    new Timestamp(System.currentTimeMillis()),
                    request.getId());
        } else {
            throw new RuntimeException("Old password is incorrect");
        }
    }
}
