package com.example.test.controller;

import com.example.test.domain.Car;
import com.example.test.dto.CarInfoUpdateRequest;
import com.example.test.exception.GlobalExceptionHandler;
import com.example.test.exception.ValidatorException;
import com.example.test.security.JwtUtil;
import com.example.test.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
@AutoConfigureMockMvc(addFilters = false)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @MockitoBean
    private CarService carService;

    @Autowired
    private ObjectMapper objectMapper;

    private Car sampleCar() {
        Car car = new Car();
        car.setCarId(1L);
        car.setName("Toyota Camry");
        car.setPrice(25000);
        car.setNotation("Test");
        car.setManufacturer("Toyota");
        car.setManufacturedYear(2023);
        return car;
    }

    @Test
    void registerCar_success() throws Exception {
        Car car = sampleCar();

        mockMvc.perform(post("/registerCar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(car)))
                .andExpect(status().isCreated());
    }

    @Test
    void registerCar_validationFailure() throws Exception {
        Car car = new Car();
        car.setName("");
        doThrow(new ValidatorException("Car name cannot be empty"))
                .when(carService).saveCar(any(Car.class));

        mockMvc.perform(post("/registerCar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(car)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void searchItems_success() throws Exception {
        when(carService.searchCars(anyString(), anyString(), anyString(), anyDouble(), anyDouble(), anyInt(), anyInt(), anyInt(), anyInt()))
                .thenReturn(List.of(sampleCar()));
        when(carService.getTotalCars()).thenReturn(1L);

        mockMvc.perform(get("/store").param("query", "Toyota"))
                .andExpect(status().isOk())
                .andExpect(header().string("X-Total-Count", "1"));
    }

    @Test
    void updateCar_success() throws Exception {
        CarInfoUpdateRequest request = new CarInfoUpdateRequest();
        request.setCarId(1L);
        request.setName("Updated");
        request.setPrice(30000);
        request.setManufacturer("Toyota");
        request.setManufacturedYear("2023");

        mockMvc.perform(put("/update-car/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void updateCar_validationFailure() throws Exception {
        CarInfoUpdateRequest request = new CarInfoUpdateRequest();
        request.setName("");
        request.setManufacturedYear("2023");
        doThrow(new ValidatorException("Car name cannot be empty"))
                .when(carService).updateCar(any(CarInfoUpdateRequest.class), eq(1L));

        mockMvc.perform(put("/update-car/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteCar_success() throws Exception {
        mockMvc.perform(delete("/deleteCar/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getCarById_found() throws Exception {
        when(carService.getCarById(1L)).thenReturn(Optional.of(sampleCar()));

        mockMvc.perform(get("/car/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Toyota Camry"));
    }

    @Test
    void getCarById_notFound() throws Exception {
        when(carService.getCarById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/car/999"))
                .andExpect(status().isNotFound());
    }
}
