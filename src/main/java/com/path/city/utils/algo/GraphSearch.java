package com.path.city.utils.algo;

import java.util.*;

public class GraphSearch {

    /**
     * Detailed implementation for the searching algorithm, utilized the BFS.
     * @param origin city name
     * @param destination city name
     * @param cityMap the adjacent list of the graph
     * @return whether the two given cities are connected
     */
    public static boolean isConnected(String origin,
                              String destination,
                              Map<String, Set<String>> cityMap) {

        Queue<String> queue = new LinkedList<>();
        queue.offer(origin);
        Set<String> visited = new HashSet<>();

        String currCity = null;
        Set<String> citySet = null;

        boolean isConnected = false;

        // start the bfs, each time get one city from the queue
        while (!queue.isEmpty()){
            currCity = queue.poll();
            citySet = cityMap.get(currCity);
            visited.add(currCity);

            // expand all unvisited neighbour cities of the current city into the queue.
            // if found the destination city, we are done.
            for (String nextCity: citySet){
                if (!visited.contains(nextCity)){
                    if (nextCity.equals(destination)){
                        isConnected = true;
                        break;
                    }

                    queue.offer(nextCity);
                }
            }

            if (isConnected){
                break;
            }
        }

        return isConnected;
    }
}
