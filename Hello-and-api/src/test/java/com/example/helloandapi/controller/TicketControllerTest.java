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
class TicketControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void post_bothFields_returns200AndConfirmation() throws Exception {
        mvc.perform(post("/tickets")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"ticketTitle":"Cannot login","description":"I am unable to login to my account"}
                                """))
                .andExpect(status().isOk())
                .andExpect(content().string("Ticket submitted successfully!"));
    }

    @Test
    void get_emptyOnFreshContext_returnsEmptyArray() throws Exception {
        mvc.perform(get("/tickets"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void postThenGet_returnsExactlyThatTicket() throws Exception {
        mvc.perform(post("/tickets")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"ticketTitle":"Cannot login","description":"I am unable to login to my account"}
                                """))
                .andExpect(status().isOk());

        mvc.perform(get("/tickets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].ticketTitle").value("Cannot login"))
                .andExpect(jsonPath("$[0].description").value("I am unable to login to my account"));
    }

    @Test
    void postTwice_getReturnsBoth() throws Exception {
        mvc.perform(post("/tickets")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"ticketTitle":"first","description":"first desc"}
                                """))
                .andExpect(status().isOk());

        mvc.perform(post("/tickets")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"ticketTitle":"second","description":"second desc"}
                                """))
                .andExpect(status().isOk());

        mvc.perform(get("/tickets"))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].ticketTitle").value("first"))
                .andExpect(jsonPath("$[1].ticketTitle").value("second"));
    }

    @Test
    void post_missingDescription_storesWithNullDescription() throws Exception {
        mvc.perform(post("/tickets")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"ticketTitle":"only title"}
                                """))
                .andExpect(status().isOk());

        mvc.perform(get("/tickets"))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].ticketTitle").value("only title"))
                .andExpect(jsonPath("$[0].description").value(org.hamcrest.Matchers.nullValue()));
    }

    @Test
    void post_emptyJsonBody_storesTicketWithBothFieldsNull() throws Exception {
        mvc.perform(post("/tickets")
                        .contentType(APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());

        mvc.perform(get("/tickets"))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].ticketTitle").value(org.hamcrest.Matchers.nullValue()))
                .andExpect(jsonPath("$[0].description").value(org.hamcrest.Matchers.nullValue()));
    }

    @Test
    void post_extraField_isSilentlyDropped() throws Exception {
        mvc.perform(post("/tickets")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"ticketTitle":"x","description":"y","priority":"high"}
                                """))
                .andExpect(status().isOk());

        mvc.perform(get("/tickets"))
                .andExpect(jsonPath("$[0].ticketTitle").value("x"))
                .andExpect(jsonPath("$[0].description").value("y"));
    }

    @Test
    void post_wrongFieldNames_storesWithNullFields() throws Exception {
        mvc.perform(post("/tickets")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"title":"x","desc":"y"}
                                """))
                .andExpect(status().isOk());

        mvc.perform(get("/tickets"))
                .andExpect(jsonPath("$[0].ticketTitle").value(org.hamcrest.Matchers.nullValue()))
                .andExpect(jsonPath("$[0].description").value(org.hamcrest.Matchers.nullValue()));
    }
}
