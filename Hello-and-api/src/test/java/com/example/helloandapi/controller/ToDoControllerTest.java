package com.example.helloandapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ToDoControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void post_bothFields_returns200AndConfirmation() throws Exception {
        mvc.perform(post("/todos")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"title":"Buy groceries","completed":false}
                                """))
                .andExpect(status().isOk())
                .andExpect(content().string("Todo added successfully"));
    }

    @Test
    void post_missingTitle_returns400() throws Exception {
        mvc.perform(post("/todos")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"completed":false}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void post_missingCompleted_returns400() throws Exception {
        mvc.perform(post("/todos")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"title":"x"}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void post_emptyBody_returns400() throws Exception {
        mvc.perform(post("/todos")
                        .contentType(APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void post_wrongFieldNames_returns400() throws Exception {
        mvc.perform(post("/todos")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"name":"x","done":false}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void post_wrongTypeForCompleted_returns400() throws Exception {
        mvc.perform(post("/todos")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"title":"x","completed":"maybe"}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void post_extraField_isSilentlyDropped() throws Exception {
        mvc.perform(post("/todos")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"title":"x","completed":false,"foo":"bar"}
                                """))
                .andExpect(status().isOk());
    }

    @Test
    void get_emptyOnFreshContext() throws Exception {
        mvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void postThenGet_returnsExactlyThatTodo() throws Exception {
        mvc.perform(post("/todos")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"title":"unique-todo","completed":true}
                                """))
                .andExpect(status().isOk());

        mvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("unique-todo"))
                .andExpect(jsonPath("$[0].completed").value(true));
    }

    @Test
    void postTwice_getReturnsBoth() throws Exception {
        mvc.perform(post("/todos")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"title":"first","completed":false}
                                """))
                .andExpect(status().isOk());

        mvc.perform(post("/todos")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"title":"second","completed":true}
                                """))
                .andExpect(status().isOk());

        mvc.perform(get("/todos"))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("first"))
                .andExpect(jsonPath("$[1].title").value("second"));
    }
}
