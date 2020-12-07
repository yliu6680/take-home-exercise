package com.path.city.utils.io;

import com.path.city.pojo.CityConnection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CityDataFileReaderTest {

    @DisplayName("Test1: read file from spring boot resource folder")
    @Test
    public void readExistedFileInResource() throws IOException {

        List<CityConnection> expected = new ArrayList<>();
        CityConnection cityConnection1 = new CityConnection("Boston", "New York");
        CityConnection cityConnection2 = new CityConnection("Newark", "Seattle");
        expected.add(cityConnection1);
        expected.add(cityConnection2);

        CityDataReader cityDataReader = new CityDataFileReader("testFileReader.txt");

        List<CityConnection> cityConnections = cityDataReader.getCityData();

        Assertions.assertEquals(expected, cityConnections);
    }

    @DisplayName("Test2: read not existed file from spring boot resource folder")
    @Test
    public void readNonExistedFile(){

        Assertions.assertThrows(FileNotFoundException.class, () -> {
            CityDataReader cityDataReader = new CityDataFileReader("testCities.txt");
            cityDataReader.getCityData();
        });
    }

    @DisplayName("Test3: read file with invalid lines, the reader will skip them")
    @Test
    public void readFileWithInvalidData() throws IOException {
        List<CityConnection> expected = new ArrayList<>();
        CityConnection cityConnection1 = new CityConnection("Boston", "Newark");
        expected.add(cityConnection1);

        CityDataReader cityDataReader = new CityDataFileReader("testFileReaderWithInvalidData.txt");
        List<CityConnection> cityConnections = cityDataReader.getCityData();

        Assertions.assertEquals(expected, cityConnections);

    }

}
