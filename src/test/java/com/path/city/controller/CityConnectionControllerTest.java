package com.path.city.controller;

import com.path.city.service.CityConnectionServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CityConnectionController.class)
public class CityConnectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CityConnectionServiceImpl cityConnectionServiceImpl;

    @DisplayName("Test1: Two connected cities are Boston and Philadelphia, expected Yes")
    @Test
    public void connectedCityTest() throws Exception {
        when(cityConnectionServiceImpl.isConnected("Boston", "Philadelphia")).thenReturn("Yes");

        RequestBuilder request = MockMvcRequestBuilders
                .get("/connected")
                .param("origin", "Boston")
                .param("destination", "Philadelphia")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("Yes"))
                .andReturn();
    }

    @DisplayName("Test2: Two unconnected cities are Chicago and Newark, expected No")
    @Test
    public void notConnectedCityTest() throws Exception {
        when(cityConnectionServiceImpl.isConnected("Chicago", "Newark")).thenReturn("Yes");

        RequestBuilder request = MockMvcRequestBuilders
                .get("/connected")
                .param("origin", "Chicago")
                .param("destination", "Newark")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("Yes"))
                .andReturn();
    }

    @DisplayName("Test3: Empty url parameters, expected No")
    @Test
    public void emptyInputTest() throws Exception {
        when(cityConnectionServiceImpl.isConnected("", "")).thenReturn("No");

        RequestBuilder request = MockMvcRequestBuilders
                .get("/connected")
                .param("origin", "")
                .param("destination", "")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("No"))
                .andReturn();
    }

    @DisplayName("Test4: Non existed city name in list is Providence, expected No")
    @Test
    public void nonExistedCityTest() throws Exception {
        when(cityConnectionServiceImpl.isConnected("Chicago", "Providence")).thenReturn("No");

        RequestBuilder request = MockMvcRequestBuilders
                .get("/connected")
                .param("origin", "Chicago")
                .param("destination", "Providence")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("No"))
                .andReturn();
    }

    @DisplayName("Test5: Invalid input are Bos--ton and New^^York, expected No")
    @Test
    public void invalidInputTest() throws Exception {
        when(cityConnectionServiceImpl.isConnected("Bos--ton", "New^^York")).thenReturn("No");

        RequestBuilder request = MockMvcRequestBuilders
                .get("/connected")
                .param("origin", "Bos--ton")
                .param("destination", "New^^York")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("No"))
                .andReturn();
    }
}
