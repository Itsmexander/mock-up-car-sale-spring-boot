package com.example.test.cucumber;

import com.example.test.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class UserStepDefinitions {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private SharedState sharedState;

    private User currentUser;

    @Given("I have a valid user with firstname {string} and email {string}")
    public void iHaveAValidUser(String firstname, String email) {
        currentUser = new User();
        currentUser.setFirstname(firstname);
        currentUser.setSurname("TestSurname");
        currentUser.setAddress("123 Test St");
        currentUser.setTelno("0123456789");
        currentUser.setEmail(email);
        currentUser.setPassword("password123");
    }

    @Given("I have a user with empty firstname")
    public void iHaveAUserWithEmptyFirstname() {
        currentUser = new User();
        currentUser.setFirstname("");
        currentUser.setSurname("Test");
        currentUser.setAddress("Test");
        currentUser.setTelno("123");
        currentUser.setEmail("test@test.com");
        currentUser.setPassword("password");
    }

    @Given("a user with email {string} exists in the database")
    public void aUserExistsInTheDatabase(String email) {
        String hashedPassword = passwordEncoder.encode("password123");
        jdbcTemplate.update(
                "INSERT INTO `user` (firstname, surname, address, telno, email, password, creation_timestamp) VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)",
                "TestUser", "TestSurname", "123 Test St", "0123456789", email, hashedPassword);
    }

    @When("I register the user")
    public void iRegisterTheUser() throws Exception {
        sharedState.setResult(mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currentUser)))
                .andReturn());
    }

    @When("I get all users")
    public void iGetAllUsers() throws Exception {
        sharedState.setResult(mockMvc.perform(get("/auth/test/getalluser"))
                .andReturn());
    }

    @And("no user should have a password field")
    public void noUserShouldHaveAPasswordField() throws Exception {
        String content = sharedState.getResult().getResponse().getContentAsString();
        assertFalse(content.contains("password"));
    }
}
