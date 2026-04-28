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
class BookControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void post_validBook_returnsBookWithGeneratedId() throws Exception {
        mvc.perform(post("/api/books")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"title":"Clean Code","author":"Robert C. Martin"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Clean Code"))
                .andExpect(jsonPath("$.author").value("Robert C. Martin"));
    }

    @Test
    void postTwice_idsIncrementFromOne() throws Exception {
        mvc.perform(post("/api/books")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"title":"first","author":"a"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        mvc.perform(post("/api/books")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"title":"second","author":"b"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2));
    }

    @Test
    void get_emptyOnFreshContext_returnsEmptyArray() throws Exception {
        mvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void postThenGetAll_returnsThatBook() throws Exception {
        mvc.perform(post("/api/books")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"title":"unique-book","author":"someone"}
                                """))
                .andExpect(status().isOk());

        mvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("unique-book"))
                .andExpect(jsonPath("$[0].author").value("someone"));
    }

    @Test
    void postThreeTimes_getAllReturnsAllInOrder() throws Exception {
        mvc.perform(post("/api/books")
                        .contentType(APPLICATION_JSON)
                        .content("{\"title\":\"a\",\"author\":\"x\"}"))
                .andExpect(status().isOk());
        mvc.perform(post("/api/books")
                        .contentType(APPLICATION_JSON)
                        .content("{\"title\":\"b\",\"author\":\"y\"}"))
                .andExpect(status().isOk());
        mvc.perform(post("/api/books")
                        .contentType(APPLICATION_JSON)
                        .content("{\"title\":\"c\",\"author\":\"z\"}"))
                .andExpect(status().isOk());

        mvc.perform(get("/api/books"))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[0].title").value("a"))
                .andExpect(jsonPath("$[1].title").value("b"))
                .andExpect(jsonPath("$[2].title").value("c"));
    }

    @Test
    void postThenGetById_returnsThatBook() throws Exception {
        mvc.perform(post("/api/books")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"title":"target","author":"x"}
                                """))
                .andExpect(jsonPath("$.id").value(1));

        mvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("target"))
                .andExpect(jsonPath("$.author").value("x"));
    }

    @Test
    void getById_picksCorrectOneAmongMany() throws Exception {
        mvc.perform(post("/api/books").contentType(APPLICATION_JSON)
                .content("{\"title\":\"a\",\"author\":\"x\"}")).andExpect(status().isOk());
        mvc.perform(post("/api/books").contentType(APPLICATION_JSON)
                .content("{\"title\":\"b\",\"author\":\"y\"}")).andExpect(status().isOk());
        mvc.perform(post("/api/books").contentType(APPLICATION_JSON)
                .content("{\"title\":\"c\",\"author\":\"z\"}")).andExpect(status().isOk());

        mvc.perform(get("/api/books/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.title").value("b"))
                .andExpect(jsonPath("$.author").value("y"));
    }

    @Test
    void getById_notFound_returns200() throws Exception {
        mvc.perform(get("/api/books/999"))
                .andExpect(status().isOk());
    }

    @Test
    void post_extraField_isSilentlyDropped() throws Exception {
        mvc.perform(post("/api/books")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"title":"x","author":"y","year":2020,"isbn":"123"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("x"))
                .andExpect(jsonPath("$.author").value("y"));
    }

    @Test
    void post_clientSuppliedId_isOverriddenByServer() throws Exception {
        mvc.perform(post("/api/books")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"id":42,"title":"x","author":"y"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
}
