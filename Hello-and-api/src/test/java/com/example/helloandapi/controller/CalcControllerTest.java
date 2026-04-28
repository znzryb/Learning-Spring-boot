package com.example.helloandapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CalcControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void add_tenAndFive_returns15() throws Exception {
        mvc.perform(get("/api/calc/add").param("a", "10").param("b", "5"))
                .andExpect(status().isOk())
                .andExpect(content().string("15"));
    }

    @Test
    void add_negativeAndPositive_returnsCorrectSum() throws Exception {
        mvc.perform(get("/api/calc/add").param("a", "-3").param("b", "8"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    void subtract_tenAndFive_returns5() throws Exception {
        mvc.perform(get("/api/calc/subtract").param("a", "10").param("b", "5"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    void subtract_smallMinusBig_returnsNegative() throws Exception {
        mvc.perform(get("/api/calc/subtract").param("a", "3").param("b", "10"))
                .andExpect(status().isOk())
                .andExpect(content().string("-7"));
    }

    @Test
    void multiply_tenAndFive_returns50() throws Exception {
        mvc.perform(get("/api/calc/multiply").param("a", "10").param("b", "5"))
                .andExpect(status().isOk())
                .andExpect(content().string("50"));
    }

    @Test
    void multiply_byZero_returnsZero() throws Exception {
        mvc.perform(get("/api/calc/multiply").param("a", "7").param("b", "0"))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    void divide_tenAndTwo_returns5() throws Exception {
        mvc.perform(get("/api/calc/divide").param("a", "10").param("b", "2"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    void divide_byZero_returnsErrorMessage() throws Exception {
        mvc.perform(get("/api/calc/divide").param("a", "10").param("b", "0"))
                .andExpect(status().isOk())
                .andExpect(content().string("Cannot divide by zero"));
    }

    @Test
    void add_missingB_returns400() throws Exception {
        mvc.perform(get("/api/calc/add").param("a", "10"))
                .andExpect(status().isBadRequest());
    }
}
