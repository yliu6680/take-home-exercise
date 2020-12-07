package com.path.city.utils.cache;

import java.util.*;

/**
 * An implementation for the city data cahe, this implementation is utilized LRU (Least Recently Used)
 * cache. Utilized LinkedHashMap in Java Collections framework as the container.
 */
public class CityDataLRUCache
        extends LinkedHashMap<String, String> implements CityDataCache {

    private int capacity;

    public CityDataLRUCache(){
        super(4, 0.75f, true);
        this.capacity = 4;
    }

    public CityDataLRUCache(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
        return size() > capacity;
    }

    /**
     * Add the city road into the cache, because there is no direction in the use case, so
     * we need to add roads with two directions.
     * @param city1 city name
     * @param city2 city name
     * @return whether or not the city pair is in the cache.
     */
    @Override
    public String getCityConnection(String city1, String city2) {
        String res = getOrDefault(city1 + ":" + city2, null);
        if (res != null) {
            return res;
        }

        res = getOrDefault(city2 + ":" + city1, null);
        if (res != null){
            return res;
        }

        return res;
    }

    @Override
    public void putCityConnection(String city1, String city2, String value) {
        put(city1 + ":" + city2, value);
    }

}


