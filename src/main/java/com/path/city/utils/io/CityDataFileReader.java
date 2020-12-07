package com.path.city.utils.io;

import com.path.city.pojo.CityConnection;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * An implementation of the CityDataReader interface, it's used the Java IO to read data from file.
 * For example: city.txt file.
 */
public class CityDataFileReader implements CityDataReader {

    private String filePath;

    public CityDataFileReader(String filePath){
        this.filePath = filePath;
    }

    /**
     * This function is to read the city list from the city.txt, the file is in the resources folder, and the path is
     * shown in the constants.properties, so it's flexible to change files content and file path.
     * It is implemented with Spring boot ResourceUtils and Java IO FileReader.
     * @return the pairs of cities that have roads, stored in an ArrayList.
     */
    @Override
    public List<CityConnection> getCityData() throws IOException {
        List<CityConnection> cityConnections = new ArrayList<>();
        ClassPathResource resource = new ClassPathResource(this.filePath);
        URL url = resource.getURL();

        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try {
            // get the file and use the bufferedReader to read the file
            inputStreamReader = new InputStreamReader(url.openStream());
            bufferedReader = new BufferedReader(inputStreamReader);

            List<String> lines = bufferedReader.lines().collect(Collectors.toList());

            for (String line: lines){
                if (!line.matches("^[A-Za-z0-9 ]+[,][A-Za-z0-9 ]+$")){
                    continue;
                }

                String[] arr = line.split(",");
                cityConnections.add(new CityConnection(arr[0].trim(), arr[1].trim()));
            }

            bufferedReader.close();
            inputStreamReader.close();

        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e){
            throw e;
        } finally {
            if (bufferedReader != null) bufferedReader.close();
            if (inputStreamReader != null) inputStreamReader.close();
        }

        return cityConnections;

    }

}
