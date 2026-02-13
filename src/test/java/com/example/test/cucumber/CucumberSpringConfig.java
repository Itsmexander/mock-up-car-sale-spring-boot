package com.example.test.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@CucumberContextConfiguration
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles({"test", "jdbc"})
public class CucumberSpringConfig {
}
