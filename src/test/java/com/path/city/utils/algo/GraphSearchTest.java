package com.path.city.utils.algo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

public class GraphSearchTest {

    public static Map<String, Set<String>> cityMap;

    @BeforeAll
    public static void init(){
        cityMap = new HashMap<>();

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
    }

    @DisplayName("Test1: test two neighbour cities, Boston and Newark, expected true")
    @Test
    public void connectedCitiesTest1(){
        Assertions.assertEquals(true,
                GraphSearch.isConnected("Boston", "Newark", cityMap));
    }

    @DisplayName("Test2: test two non-neighbour connected cities, San Antonio and Dallas, expected true")
    @Test
    public void connectedCitiesTest2(){
        Assertions.assertEquals(true,
                GraphSearch.isConnected("San Antonio", "Dallas", cityMap));
    }

    @DisplayName("Test3: test two unconnected cities, Chicago and Newark, expected false")
    @Test
    public void connectedCitiesTest3(){
        Assertions.assertEquals(false,
                GraphSearch.isConnected("Chicago", "Newark", cityMap));
    }

}
