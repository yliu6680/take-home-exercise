package com.path.city.utils.cache;

import com.path.city.pojo.CityConnection;

public interface CityDataCache {

    public String getCityConnection(String city1, String city2);

    public void putCityConnection(String city1, String city2, String value);

}
