package com.path.city.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityConnectionControllerITTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @DisplayName("Test1: Integrated test with connected cities, Boston and Philadelphia")
    @Test
    public void connectedCityTest(){

        String response =
                this.restTemplate.getForObject("/connected?origin={city1}&destination={city2}",
                        String.class, "Boston", "Philadelphia");

        Assertions.assertEquals("Yes", response);

    }

    @DisplayName("Test2: Integrated test with unconnected cities, Chicago and Newark")
    @Test
    public void nonConnectedCityTest(){

        String response =
                this.restTemplate.getForObject("/connected?origin={city1}&destination={city2}",
                        String.class, "Chicago", "Newark");

        Assertions.assertEquals("No", response);

    }

    @DisplayName("Test3: Integrated test with invalid input, Bos--ton and New^^York")
    @Test
    public void invalidCityTest(){

        String response =
                this.restTemplate.getForObject("/connected?origin={city1}&destination={city2}",
                        String.class, "Bos--ton", "New^^York");

        Assertions.assertEquals("No", response);

    }
}
