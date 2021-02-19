package com.challenge.quasarfire.communications.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.hamcrest.Matchers.is;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @author Andres Ortiz andresortiz248@gmail.com
 * */
@AutoConfigureMockMvc
@SpringBootTest
public class ReceiverControllerTest {

    @Autowired
    private MockMvc mvc;

    /**
     * Test endpoint /quasarfire/topsecret works,
     * get an ok message, the position and mesagge from the emitter
     */
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

        mvc.perform(post( "/quasarfire/topsecret")
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

    /**
     * Test endpoint /quasarfire/topsecret returns a not found
     */
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

        mvc.perform(post( "/quasarfire/topsecret")
                .with(csrf())
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    /**
     * Test endpoint /quasarfire/topsecret_split/{satellite_name} works,
     * get an ok message, the position and mesagge received from the satellite
     */
    @Test
    public void testGetLocationBySatellite() throws Exception{
        String jsonBody =
                "{" +
                        "      \"distance\":500.0, " +
                        "      \"message\": [\"this\",\"\",\"\",\"secret\",\"\"] " +
                        "}";

        mvc.perform(get( "/quasarfire/topsecret_split/kenobi")
                .with(csrf())
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.position.x", is(-500.0)))
                .andExpect(jsonPath("$.position.y", is(-200.0)))
                .andExpect(jsonPath("$.message", is("this secret")))
                .andReturn();
    }

    /**
     * Test endpoint /quasarfire/topsecret_split/{satellite_name} returns a not found
     */
    @Test
    public void testGetLocationBySatelliteNotFound() throws Exception{
        String jsonBody =
                "{" +
                        "      \"distance\":500.0, " +
                        "      \"message\": [\"this\",\"\",\"\",\"secret\",\"\"] " +
                        "}";

        mvc.perform(get( "/quasarfire/topsecret_split/solo")
                .with(csrf())
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }
}
