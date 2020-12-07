package com.path.city.utils.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CityDataCacheTest {

    @DisplayName("Test1: get null item from cache")
    @Test
    public void getNullItemTest(){

        CityDataCache cache = new CityDataLRUCache(3);
        String value = cache.getCityConnection("Boston", "Newark");

        Assertions.assertNull(value);
    }

    @DisplayName("Test2: set and get item")
    @Test
    public void setAndGetItemTest(){
        CityDataCache cache = new CityDataLRUCache(3);
        cache.putCityConnection("Boston", "Newark", "Yes");

        String value = cache.getCityConnection("Boston", "Newark");

        Assertions.assertEquals("Yes", value);
    }

    @DisplayName("Test3: set and get item with reverse order of city names")
    @Test
    public void setAndGetItemReverseOrderTest(){
        CityDataCache cache = new CityDataLRUCache(3);
        cache.putCityConnection("Boston", "Newark", "Yes");

        String value = cache.getCityConnection("Newark", "Boston");

        Assertions.assertEquals("Yes", value);
    }

    @DisplayName("Test4: set the key twice")
    @Test
    public void setItemTwiceTest(){
        CityDataCache cache = new CityDataLRUCache(3);
        cache.putCityConnection("Boston", "Newark", "Yes");
        cache.putCityConnection("Boston", "Newark", "No");

        String value = cache.getCityConnection("Newark", "Boston");

        Assertions.assertEquals("No", value);
    }

    @DisplayName("Test5: evict data from cache")
    @Test
    public void evictData(){
        CityDataCache cache = new CityDataLRUCache(3);
        cache.putCityConnection("Boston", "Newark", "No");
        cache.putCityConnection("Newark", "New York", "Yes");
        cache.putCityConnection("Houston", "Seattle", "Yes");
        cache.putCityConnection("Boston", "Houston", "No");

        Assertions.assertNull(cache.getCityConnection("Boston", "Newark"));
        Assertions.assertEquals("Yes", cache.getCityConnection("Newark", "New York"));
        Assertions.assertEquals("Yes", cache.getCityConnection("Houston", "Seattle"));
        Assertions.assertEquals("No", cache.getCityConnection("Boston", "Houston"));

    }

    @DisplayName("Test6: get item and then evict data from cache")
    @Test
    public void getItemAndEvictData(){
        CityDataCache cache = new CityDataLRUCache(3);
        cache.putCityConnection("Boston", "Newark", "No");
        cache.putCityConnection("Newark", "New York", "Yes");
        cache.putCityConnection("Houston", "Seattle", "Yes");
        cache.getCityConnection("Boston", "Newark");
        cache.putCityConnection("Boston", "Houston", "No");

        Assertions.assertEquals("No", cache.getCityConnection("Boston", "Newark"));
        Assertions.assertNull(cache.getCityConnection("Newark", "New York"));
        Assertions.assertEquals("Yes", cache.getCityConnection("Houston", "Seattle"));
        Assertions.assertEquals("No", cache.getCityConnection("Boston", "Houston"));

    }
}
