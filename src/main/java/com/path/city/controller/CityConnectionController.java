package com.path.city.controller;

import com.path.city.service.CityConnectionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
public class CityConnectionController {

    @Autowired
    private CityConnectionService cityConnectionService;

    @GetMapping("/hello")
    public String hello(){

        System.out.println(cityConnectionService.isConnected("Boston", "Newark"));
        System.out.println(cityConnectionService.isConnected("Boston", "Philadelphia"));
        System.out.println(cityConnectionService.isConnected("Boston", "Albany"));

        return "hello";
    }

    @GetMapping("/connected")
    public String isConnected(@PathParam("origin") String origin,
                               @PathParam("destination") String destination){

        if (StringUtils.isBlank(origin) || StringUtils.isBlank(destination)){
            return "No";
        }

        return cityConnectionService.isConnected(origin, destination);

    }
}
