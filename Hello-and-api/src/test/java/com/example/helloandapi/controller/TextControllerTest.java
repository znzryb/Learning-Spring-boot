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
class TextControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void uppercase_lowerInput_returnsUpper() throws Exception {
        mvc.perform(get("/api/text/uppercase").param("text", "hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("HELLO"));
    }

    @Test
    void uppercase_mixedCase_returnsAllUpper() throws Exception {
        mvc.perform(get("/api/text/uppercase").param("text", "HeLLo WoRLd"))
                .andExpect(status().isOk())
                .andExpect(content().string("HELLO WORLD"));
    }

    @Test
    void uppercase_alreadyUpper_returnsSame() throws Exception {
        mvc.perform(get("/api/text/uppercase").param("text", "ALREADY"))
                .andExpect(status().isOk())
                .andExpect(content().string("ALREADY"));
    }

    @Test
    void uppercase_withDigitsAndPunctuation_keepsThemUnchanged() throws Exception {
        mvc.perform(get("/api/text/uppercase").param("text", "abc123!?"))
                .andExpect(status().isOk())
                .andExpect(content().string("ABC123!?"));
    }

    @Test
    void lowercase_upperInput_returnsLower() throws Exception {
        mvc.perform(get("/api/text/lowercase").param("text", "HELLO"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello"));
    }

    @Test
    void lowercase_mixedCase_returnsAllLower() throws Exception {
        mvc.perform(get("/api/text/lowercase").param("text", "HeLLo WoRLd"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello world"));
    }

    @Test
    void lowercase_alreadyLower_returnsSame() throws Exception {
        mvc.perform(get("/api/text/lowercase").param("text", "already"))
                .andExpect(status().isOk())
                .andExpect(content().string("already"));
    }

    @Test
    void lowercase_withDigitsAndPunctuation_keepsThemUnchanged() throws Exception {
        mvc.perform(get("/api/text/lowercase").param("text", "ABC123!?"))
                .andExpect(status().isOk())
                .andExpect(content().string("abc123!?"));
    }

    @Test
    void uppercase_emptyString_returnsEmpty() throws Exception {
        mvc.perform(get("/api/text/uppercase").param("text", ""))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void uppercase_missingParam_returns400() throws Exception {
        mvc.perform(get("/api/text/uppercase"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void lowercase_missingParam_returns400() throws Exception {
        mvc.perform(get("/api/text/lowercase"))
                .andExpect(status().isBadRequest());
    }
}
