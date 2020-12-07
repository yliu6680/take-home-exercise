package com.path.city.service;

import com.path.city.pojo.CityConnection;
import com.path.city.utils.ApplicationPropertyResource;
import com.path.city.utils.cache.CityDataCache;
import com.path.city.utils.cache.CityDataLRUCache;
import com.path.city.utils.io.CityDataFileReader;
import com.path.city.utils.io.CityDataReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import com.path.city.utils.algo.GraphSearch;

/**
 * An implementation of the problem. Utilized Breath First Search in this implementation.
 * It contains two main functionalities, one is to read the data by utilizing the CityDataReader and
 * create the undirected graph by using adjacent list, the second functionality is to determine whether
 * two input cities have roads between them.
 *
 */
@Service
public class CityConnectionServiceImpl implements CityConnectionService{

    @Autowired
    private ApplicationPropertyResource propertyResource;

    private CityDataReader cityDataReader;

    private CityDataCache cityDataCache;

    private Map<String, Set<String>> cityMap = new HashMap<>();

    /**
     * Create adjacent list of the undirected graph. This function uses the CityDataReader to get city data
     * list, and then build the graph.
     * The adjacent list in this implementation is utilizing hashmap with hashset.
     */
    @PostConstruct
    public void createGraph() throws IOException {

        cityDataReader = new CityDataFileReader(propertyResource.getCitydataFilePath());

        cityDataCache = new CityDataLRUCache(propertyResource.getLRUCacheSize());

        List<CityConnection> cityConnections = cityDataReader.getCityData();

        for (CityConnection cityConnection: cityConnections){
            String origin = cityConnection.getOrigin();
            String destination = cityConnection.getDestination();

            // find all neighbour cities of current city
            Set<String> destinationSet = cityMap.getOrDefault(origin, new HashSet<>());
            destinationSet.add(destination);
            cityMap.put(origin, destinationSet);

            Set<String> originSet = cityMap.getOrDefault(destination, new HashSet<>());
            originSet.add(origin);
            cityMap.put(destination, originSet);
        }

        System.out.println(this.cityMap);
    }

    /**
     * Utilized BFS to search the graph, and find is there any destination for the origin.
     * Implemented BFS with a queue, and also maintain a hashset to record the visited city.
     * Implemented cache to improve the performance.
     * @param origin city name
     * @param destination city name
     * @return if there exists any road between the origin and destination, return "Yes",
     *         otherwise, return "no".
     *         if input city is not existed in the graph, then return "no".
     */
    @Override
    public String isConnected(String origin, String destination){
        // if there is no record in the city list, or input cities are same, then return false
        if (!this.cityMap.containsKey(origin)
                || !this.cityMap.containsKey(destination)
                || origin.equals(destination)){
            return "No";
        }

        // get from cache
        String cacheRes = cityDataCache.getCityConnection(origin, destination);
        if (cacheRes != null){
            return cacheRes;
        }

        boolean isConnected = GraphSearch.isConnected(origin, destination, this.cityMap);

        if (isConnected){
            cityDataCache.putCityConnection(origin, destination, "Yes");
            return "Yes";
        } else {
            cityDataCache.putCityConnection(origin, destination, "No");
            return "No";
        }
    }

    /**
     * getter and setter are used for testing
     */
    public Map<String, Set<String>> getCityMap() {
        return cityMap;
    }

    public void setCityMap(Map<String, Set<String>> cityMap) {
        this.cityMap = cityMap;
    }

    public CityDataCache getCityDataCache() {
        return cityDataCache;
    }

    public void setCityDataCache(CityDataCache cityDataCache) {
        this.cityDataCache = cityDataCache;
    }
}
