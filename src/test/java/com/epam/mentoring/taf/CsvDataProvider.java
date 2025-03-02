package com.epam.mentoring.taf;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.testng.annotations.DataProvider;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CsvDataProvider {

    public static Iterator<Object[]> getFilteredUserData(String testType) throws IOException {
        List<Object[]> testData = new ArrayList<>();

        try (Reader reader = new FileReader("src/test/resources/data/userdata.csv");
             CSVParser csvParser = CSVFormat.DEFAULT.builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .get()
                     .parse(reader)) {

            for (CSVRecord record : csvParser) {
                String type = record.get("testType");


                if (type.equalsIgnoreCase(testType)) {
                    String username = record.get("username");
                    String email = record.get("email");
                    String password = record.get("password");
                    String expectedStatus = record.get("expectedStatus");
                    String expectedMessage = record.get("expectedMessage");

                    testData.add(new Object[]{username, email, password, Integer.parseInt(expectedStatus), expectedMessage});
                }
            }
        }
        return testData.iterator();
    }

    @DataProvider(name = "validUserData")
    public static Iterator<Object[]> getValidUserData() throws IOException {
        return getFilteredUserData("valid");
    }

    @DataProvider(name = "invalidPassword")
    public static Iterator<Object[]> getInValidUserData() throws IOException {
        return getFilteredUserData("invalidpassword");
    }
}
