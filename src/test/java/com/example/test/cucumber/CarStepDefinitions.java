package com.example.test.cucumber;

import com.example.test.domain.Car;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class CarStepDefinitions {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SharedState sharedState;

    private Car currentCar;
    private Long savedCarId;

    @Before
    public void cleanUp() {
        jdbcTemplate.update("DELETE FROM car");
    }

    @Given("I have a valid car with name {string} and price {int} and year {int}")
    public void iHaveAValidCar(String name, int price, int year) {
        currentCar = new Car();
        currentCar.setName(name);
        currentCar.setPrice(price);
        currentCar.setManufacturedYear(year);
        currentCar.setManufacturer("TestMfg");
        currentCar.setNotation("Test notation");
    }

    @Given("I have a car with empty name")
    public void iHaveACarWithEmptyName() {
        currentCar = new Car();
        currentCar.setName("");
        currentCar.setPrice(25000);
        currentCar.setManufacturedYear(2023);
        currentCar.setManufacturer("TestMfg");
    }

    @Given("a car with name {string} exists in the database")
    public void aCarExistsInTheDatabase(String name) {
        jdbcTemplate.update(
                "INSERT INTO car (name, price, notation, manufacturer, manufacturedYear, creation_timestamp) VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)",
                name, 25000, "Test", "TestMfg", 2023);
        savedCarId = jdbcTemplate.queryForObject("SELECT MAX(carId) FROM car", Long.class);
    }

    @When("I register the car")
    public void iRegisterTheCar() throws Exception {
        sharedState.setResult(mockMvc.perform(post("/registerCar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currentCar)))
                .andReturn());
    }

    @When("I search for cars with query {string}")
    public void iSearchForCars(String query) throws Exception {
        sharedState.setResult(mockMvc.perform(get("/store").param("query", query))
                .andReturn());
    }

    @When("I get the car by its ID")
    public void iGetTheCarByItsId() throws Exception {
        sharedState.setResult(mockMvc.perform(get("/car/" + savedCarId))
                .andReturn());
    }

    @When("I get a car with ID {long}")
    public void iGetACarWithId(long id) throws Exception {
        sharedState.setResult(mockMvc.perform(get("/car/" + id))
                .andReturn());
    }

    @When("I delete the car")
    public void iDeleteTheCar() throws Exception {
        sharedState.setResult(mockMvc.perform(delete("/deleteCar/" + savedCarId))
                .andReturn());
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int status) throws Exception {
        assertNotNull(sharedState.getResult(), "MvcResult should not be null");
        if (sharedState.getResult().getResponse().getStatus() != status) {
            String body = sharedState.getResult().getResponse().getContentAsString();
            assertEquals(status, sharedState.getResult().getResponse().getStatus(),
                    "Response body: " + body);
        }
    }

    @And("the car name should be {string}")
    public void theCarNameShouldBe(String name) throws Exception {
        String content = sharedState.getResult().getResponse().getContentAsString();
        assertTrue(content.contains(name));
    }
}
