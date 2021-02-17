package com.challenge.quasarfire.communications.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.hamcrest.Matchers.is;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@SpringBootTest
public class ReceiverControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testGetLocationAndMessage() throws Exception{
        String jsonBody =
                "{" +
                        "  \"satellites\":[ " +
                        "    { " +
                        "      \"name\":\"kenobi\", " +
                        "      \"distance\":500.0, " +
                        "      \"message\": [\"this\",\"\",\"\",\"secret\",\"\"] " +
                        "    }, " +
                        "    { " +
                        "      \"name\":\"skywalker\", " +
                        "      \"distance\":570.0, " +
                        "      \"message\": [\"\",\"is\",\"\",\"\",\"message\"] " +
                        "    }, " +
                        "    { " +
                        "      \"name\":\"sato\", " +
                        "      \"distance\":850.0, " +
                        "      \"message\": [\"this\",\"\",\"a\",\"\",\"\"] " +
                        "    } " +
                        "  ] " +
                "}";

        MvcResult result = mvc.perform(post( "/quasarfire/topsecret")
                .with(csrf())
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.position.x", is(-332.6)))
                .andExpect(jsonPath("$.position.y", is(271.3)))
                .andExpect(jsonPath("$.message", is("this is a secret message")))
                .andReturn();
    }

    @Test
    public void testGetLocationAndMessageNotFound() throws Exception{
        String jsonBody =
                "{" +
                        "  \"satellites\":[ " +
                        "    { " +
                        "      \"name\":\"kenobi\", " +
                        "      \"distance\":100.0, " +
                        "      \"message\": [\"this\",\"\",\"\",\"secret\",\"\"] " +
                        "    }, " +
                        "    { " +
                        "      \"name\":\"skywalker\", " +
                        "      \"distance\":115.5, " +
                        "      \"message\": [\"\",\"is\",\"\",\"\",\"message\"] " +
                        "    }, " +
                        "    { " +
                        "      \"name\":\"sato\", " +
                        "      \"distance\":142.7, " +
                        "      \"message\": [\"this\",\"\",\"a\",\"\",\"\"] " +
                        "    } " +
                        "  ] " +
                        "}";

        MvcResult result = mvc.perform(post( "/quasarfire/topsecret")
                .with(csrf())
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }
}
