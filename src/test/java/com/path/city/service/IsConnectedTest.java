package com.path.city.service;

import com.path.city.utils.cache.CityDataLRUCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class IsConnectedTest {

    public static CityConnectionServiceImpl cityConnectionServiceImpl
            = new CityConnectionServiceImpl();

    @BeforeAll
    public static void init(){
        Map<String, Set<String>> cityMap = new HashMap<>();

        cityMap.put("New York", new HashSet<>(Arrays.asList("Boston")));
        cityMap.put("San Fransisco", new HashSet<>(Arrays.asList("Chicago")));
        cityMap.put("Seattle", new HashSet<>(Arrays.asList("Los Angeles")));
        cityMap.put("San Antonio", new HashSet<>(Arrays.asList("Chicago")));
        cityMap.put("Chicago", new HashSet<>(Arrays.asList("San Fransisco", "San Antonio", "Dallas")));
        cityMap.put("Newark", new HashSet<>(Arrays.asList("Philadelphia", "Boston")));
        cityMap.put("Trenton", new HashSet<>(Arrays.asList("Albany")));
        cityMap.put("Los Angeles", new HashSet<>(Arrays.asList("Seattle", "Irvine")));
        cityMap.put("Irvine", new HashSet<>(Arrays.asList("Los Angeles")));
        cityMap.put("Dallas", new HashSet<>(Arrays.asList("Chicago")));
        cityMap.put("Philadelphia", new HashSet(Arrays.asList("Newark")));
        cityMap.put("Boston", new HashSet<>(Arrays.asList("New York", "Newark", "Houston")));
        cityMap.put("Albany", new HashSet<>(Arrays.asList("Trenton")));
        cityMap.put("Houston", new HashSet<>(Arrays.asList("Boston")));

        cityConnectionServiceImpl.setCityMap(cityMap);
        cityConnectionServiceImpl.setCityDataCache(new CityDataLRUCache(3));

    }

    @DisplayName("Test1: test two neighbour cities, Boston and Newark, expected Yes")
    @Test
    public void neighbourCitiesTest(){
        Assertions.assertEquals("Yes",
                                cityConnectionServiceImpl.isConnected("Boston", "Newark"));
    }

    @DisplayName("Test2: test two non-neighbour connected cities, San Antonio and Dallas, expected Yes")
    @Test
    public void nonNeighbourConnectedCitiesTest(){
        Assertions.assertEquals("Yes",
                cityConnectionServiceImpl.isConnected("San Antonio", "Dallas"));
    }

    @DisplayName("Test3: test two unconnected cities, Chicago and Newark, expected false")
    @Test
    public void unconnectedCitiesTest(){
        Assertions.assertEquals("No",
                cityConnectionServiceImpl.isConnected("Chicago", "Newark"));
    }

    @DisplayName("Test4: test non-existed city name in list is Providence, expected No")
    @Test
    public void nonExsitedCitiesTest(){
        Assertions.assertEquals("No",
                cityConnectionServiceImpl.isConnected("Chicago", "Providence"));
    }

    @DisplayName("Test5: test invalid city names are Bos^^ton and New%20York, expected No")
    @Test
    public void invalidCityNamesTest(){
        Assertions.assertEquals("No",
                cityConnectionServiceImpl.isConnected("Bos^^ton", "New%20York"));
    }
}
