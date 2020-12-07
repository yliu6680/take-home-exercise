package com.path.city.service;

import com.path.city.utils.ApplicationPropertyResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreatGraphTest {

    @InjectMocks
    CityConnectionServiceImpl cityConnectionServiceImpl;

    @Mock
    ApplicationPropertyResource applicationPropertyResource;

    @DisplayName("Test1: create adjacent list, create graph with with single connection")
    @Test
    public void createSingleConnectionGraphTest() throws IOException {
        when(applicationPropertyResource.getCitydataFilePath()).thenReturn("testCreateGraph1.txt");
        when(applicationPropertyResource.getLRUCacheSize()).thenReturn(3);

        cityConnectionServiceImpl.createGraph();

        Map<String, Set<String>> expected = new HashMap<>();
        expected.put("Boston", new HashSet<>(Arrays.asList("New York")));
        expected.put("New York", new HashSet<>(Arrays.asList("Boston")));
        expected.put("Newark", new HashSet<>(Arrays.asList("Seattle")));
        expected.put("Seattle", new HashSet<>(Arrays.asList("Newark")));

        Assertions.assertEquals(expected, cityConnectionServiceImpl.getCityMap());

    }

    @DisplayName("Test2: create adjacent list, create a graph with multiple connections")
    @Test
    public void createMultipleConnectionsGraphTest() throws IOException {
        when(applicationPropertyResource.getCitydataFilePath()).thenReturn("testCreateGraph2.txt");
        when(applicationPropertyResource.getLRUCacheSize()).thenReturn(3);

        cityConnectionServiceImpl.createGraph();

        Map<String, Set<String>> expected = new HashMap<>();
        expected.put("Boston", new HashSet<>(Arrays.asList("Newark")));
        expected.put("Seattle", new HashSet<>(Arrays.asList("Newark")));
        expected.put("Houston", new HashSet<>(Arrays.asList("Newark")));
        expected.put("Newark", new HashSet<>(Arrays.asList("Boston", "Seattle", "Houston")));

        Assertions.assertEquals(expected, cityConnectionServiceImpl.getCityMap());

    }

    @DisplayName("Test3: create empty graph")
    @Test
    public void createEmptyGraphTest() throws IOException {
        when(applicationPropertyResource.getCitydataFilePath()).thenReturn("testCreateGraph3.txt");
        when(applicationPropertyResource.getLRUCacheSize()).thenReturn(3);

        cityConnectionServiceImpl.createGraph();

        Map<String, Set<String>> expected = new HashMap<>();

        Assertions.assertEquals(expected, cityConnectionServiceImpl.getCityMap());

    }

}
