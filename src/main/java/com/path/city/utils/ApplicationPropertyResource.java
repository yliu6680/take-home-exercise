package com.path.city.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * This class read the properties from the constans.properties file in the resources, developer could cahnge
 * the parameter from the properties file, and injected it to other classes. It decoupled the dependency of the
 * configuration parameter and the application.
 *
 */
@Component
@PropertySource("classpath:constants.properties")
@ConfigurationProperties
public class ApplicationPropertyResource {
    @Value("${citydata.file.path}")
    private String citydataFilePath;

    @Value("${cache.LRUCache.size}")
    private int LRUCacheSize;

    public String getCitydataFilePath() {
        return citydataFilePath;
    }

    public void setCitydataFilePath(String citydataFilePath) {
        this.citydataFilePath = citydataFilePath;
    }

    public int getLRUCacheSize() {
        return LRUCacheSize;
    }

    public void setLRUCacheSize(int LRUCacheSize) {
        this.LRUCacheSize = LRUCacheSize;
    }
}
