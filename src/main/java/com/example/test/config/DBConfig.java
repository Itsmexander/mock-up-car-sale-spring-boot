package com.example.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class DBConfig {

    @Bean
    public Connection connection() throws SQLException {
        // Replace with your actual connection creation logic
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/test1", "root", "");
    }
}
