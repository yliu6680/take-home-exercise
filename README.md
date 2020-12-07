# City Connection Take Home Exercise

By [Paul Liu](mailto:paulliu6680@gmail.com)

This take home exercise is from [David Parkes](dparkes@zigatta.com) at Zigatta.

## Instructions

1. Navigate to [repo](https://github.com/yliu6680/take-home-exercise.git)
2. Clone locally using
   `git clone https://github.com/yliu6680/take-home-exercise.git`
3. Install dependencies using `mvn install`
4. Run tests using `mvn test`
5. Go to the target, and start the application server using `java -jar ***.jar`
6. Open browser or to other test tools, and navigate to [http://localhost:8080/connected?origin=Boston&destination=Newark](http://localhost:8080/connected?origin=Boston&destination=Newark)
7. Or import the maven project into IDE to run in the development environment.

## Environments

The challenge is implemented by Spring Boot 2, Java 8. I also utilized Spring Boot, Junit, Mockito to generate unit test and integration test for the application. The project is built by Maven.

## Basic Requirements

### Build a Spring Boot application that could read cities data from file (.txt file).

I added this functionality in the Spring Boot application with life cycle method, so the data will be only loaded one time when the application is initializing. And the data will be interpreted into a non-directed graph. I utilized hash map and hash set to store the map as adjacent lists.

The city.txt file is stored in the resources folder of the maven project, users could replace the txt file with their own.

### Implemented Breath First Search (BFS) algorithm to search the possible roads between two different input cities.

I utilized BFS to search whether the two cities are connected in the graph from the adjacent list, more details could be found in the analysis section.

### Built controller to let user utilize GET request and URL parameter to perform the search.

I constructed RESTful API to let users to call the related controllers and service. I have added validation steps in controller and service layer, so if the input parameter is not valid, the application will return No connections.

### Created tests

I created more than 20 unit tests and integration tests for the application, covered nearly all classes and methods of the controller, service, and utils code.

## Other Bonus

### Add a cache to improve the performance

I added a cache layer for the application. So once the application is started, every valid query and result of the cities connection will be stored into a cache. I implemented the cache with LinkedHashMap in Java Collection framework, and the cache is a LRU cache. Because LRU cache could help to store the hot data when user query the data in the graph, and next time the same query is called, then we need not to perform the query algorithm again.

## Demonstration Of The Application

The whole cities data in the city.txt file is listed below:
```
Boston, New York
Philadelphia, Newark
Newark, Boston
Trenton, Albany
Los Angeles, Irvine
Seattle, Los Angeles
Houston, Boston
Chicago, San Fransisco
San Antonio, Chicago
Dallas, Chicago
```

The application could be called by using the follow URL pattern, http://localhost:8080?origin={city1}&destination={city2}.

### Normal test cases
Basic test cases between Boston and Newark, Boston and Philadelphia, Philadelphia and Trenton, and non existed cities are shown below.

![image](https://github.com/yliu6680/imooc-news-config/blob/master/Boston_Newark.png)

![image](https://github.com/yliu6680/imooc-news-config/blob/master/Bos_Phi.png)

![image](https://github.com/yliu6680/imooc-news-config/blob/master/Phi_Alb.png)

![image](https://github.com/yliu6680/imooc-news-config/blob/master/Non_Exi.png)

### Invalid test cases
Invalid test cases, including invalid input, empty parameter, and null parameter are shown below.

![image](https://github.com/yliu6680/imooc-news-config/blob/master/Invalid_Name.png)

![image](https://github.com/yliu6680/imooc-news-config/blob/master/Empty_Name.png)

![image](https://github.com/yliu6680/imooc-news-config/blob/master/Null_Input.png)

## Analysis And Implementations

### Implementation of Interpreting Data To Graph

I utilized the Java IO package to read the city.txt. 

Hashmap and Hashset is used to store the graph as adjacent list. Hashmap's key will be the heads of all lists, and value will be the adjacent list. Hashset will store the adjacent list for each head. In each line, I will format the string and split it into to cities name, and try to update the adjacent list. Each city name could be head of the adjacent list, so we need to find the list and add another city name in the hashset.

### Implementation Of The Searching Algorithm

I implemented the algorithm by Breath First Search, it's an algorithm first expand all neighbor nodes of the origin node, and then go deeper of the graph.

The data structure I used for my algorithm is queue, and implemented by Java LinkedList. I also used hashset to record all visited node, in case there are any loops in the graph. The stops could be describe as follow:

   1. Add the origin city name in the queue.
   2. Poll the queue, and get a city name. 
   3. Find the city name in the heads of all adjacent lists, all heads are the keys of the hashmap. 
   4. Find the adjacent list for the head.
   5. Iterate the hashset. If we find the destination city name in the hashset, then we are done. 
   6. Otherwise, if the city name is not in the visited set, then add the city name in to the queue and visited hashset.
   7. Repeat the algorithm from step.2 - step.6, until the queue is empty or we find the final result.
   8. If we still not find the result, then the two cities are not connected.

### Implementation Of The Cache

#### LRU
I implemented the Least Recently Used cache with LinkedHashMap. The operations for the cache is shown below:
   
   1. Initialize  the LRU cache with a capacity.
   2. Get item from the HashMap, if it exists, then we need to swap the item with the last node in the LinkedList of the LinkedHashMap.
   3. Put item in the HashMap, if the capacity has already full, then remove the first node in the LinkedList of LinkedHashMap.

So each time the recently used item will be updated to the last node in the LinkedList, and will be kept for longer time. The item will be replaced based on the last time it is used by the user. 

#### Cache Key Value Pair

I store the the two cities names and separated with ":" as the key in the cache, and the searching result of the BFS as the value in the cache.

Because the graph is non-directional, so we could have two ways to store the cities name in the cache. For example, there are an input the origin city is A, and destination city is B.

The first way is to store both pairs of city in the cache, so we will have "A:B", "B:A" as key in our cache. So each time when we try to search in the cache, we could just search one time in the cache. The second way is only store one pair of city in the cache, se we only have "A:B" as key in our cache. So each time when we try to search the cache, we could search two times in the cache, one for "A:B", and the another one for "B:A".

I utilized the second way, because it could save space in the cache memory. 

So in the end, if user search the cities A, B in our application, and they have connection, then the cache will save ***{ key: "A:B", value: "Yes" }***. If another user search cities B, A in our application, we will firstly try to get value from cache by ***cache.get("B:A")***, and then try with ***cache.get("A:B")***, and we could get the value. 

### Analysis Of The Algorithm

Assumes there are ***N*** lines in the city.txt file, ***M*** city names are in the file, and the capacity of the cache is ***C***. 
Assumes hashmap and hashset will work perfectly, so each operation ill only cost O(1).
Java IO package will not be discussed in this section.

#### Generate Graph
The time complexity of reading file will be O(N), because there are totally N lines in the file, and we only used O(1) operation per line.

However, each time, the application only read the file one time, and then store the graph in memory. So it will not affect a lot for the RESTful API.

#### Graph Search
The ***time complexity*** of the BFS is ***O(M)***, because the worst case will search whole graph.
The ***space complexity*** of the BFS is ***O(M)***, because in the worst case, the queue has to store every node of the graph, and the whole graph has M nodes,

#### Cache
The ***time complexity*** of the BFS is ***O(1)***, because the worst case will search whole graph.
The ***space complexity*** of the BFS is ***O(1)***, because each operation doesn't need extra space.

So with the cache, user could finally get better time complexity, and for each query, they could have time complexity smaller than ***O(M)***, where M is the number of city in the data file.

### Parameters validation

Parameter validations are added in the controller layer and the service layer, so we could get No Connection response when user's request parameters are not valid.

### Tests 

I wrote unit test cases for the graph search algorithm, and also test it in the service layer and controller layer with stubs, mockito, and MockMvc. I also utilized Spring Boot to generate integration test for the whole RESTful API. 

### Extensible Interfaces

I created CityDataReader interface and CityDataCache interface, and implements them with Java IO and LRU cache in this application. It could be easy to add more implementation based on the interfaces to get the cities data from database, cloud storage, and cache from other cache like LFU cache, or database Redis or cloud cache.

