package com.example.helloandapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void getAll_returnsAllThreeProducts() throws Exception {
        mvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0]").value("Laptop"))
                .andExpect(jsonPath("$[1]").value("Mobile"))
                .andExpect(jsonPath("$[2]").value("Headphones"));
    }

    @Test
    void getById_zero_returnsLaptop() throws Exception {
        mvc.perform(get("/api/products/0"))
                .andExpect(status().isOk())
                .andExpect(content().string("Laptop"));
    }

    @Test
    void getById_one_returnsMobile() throws Exception {
        mvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Mobile"));
    }

    @Test
    void getById_two_returnsHeadphones() throws Exception {
        mvc.perform(get("/api/products/2"))
                .andExpect(status().isOk())
                .andExpect(content().string("Headphones"));
    }

    @Test
    void getById_outOfBounds_returnsNotFoundMessage() throws Exception {
        mvc.perform(get("/api/products/3"))
                .andExpect(status().isOk())
                .andExpect(content().string("Product not found"));
    }

    @Test
    void getById_negative_returnsNotFoundMessage() throws Exception {
        mvc.perform(get("/api/products/-1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Product not found"));
    }

    @Test
    void getById_nonInteger_returns400() throws Exception {
        mvc.perform(get("/api/products/abc"))
                .andExpect(status().isBadRequest());
    }
}
