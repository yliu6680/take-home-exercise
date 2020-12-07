package com.path.city.utils.io;

import com.path.city.pojo.CityConnection;

import java.io.IOException;
import java.util.List;

/**
 * An interface to hold a data reader for the city list.
 *
 * Currently implemented with the Java IO methods. Could extend the interface to get the city list data from
 * database, cloud storage, etc.
 *
 */
public interface CityDataReader {

    public List<CityConnection> getCityData() throws IOException;
}
