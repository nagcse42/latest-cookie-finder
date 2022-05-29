package com.assignment.latestcookiefinder.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class FileUtils {
    static Logger logger = LoggerFactory.getLogger(FileUtils.class);
    private static final String BASE_FILE_PATH = "src/main/resources/";

    public Map<Date, String> fetchCookieMapFromFile(String fileName, DateFormat dateFormat) throws IOException, ParseException {
        String csvFilePath = BASE_FILE_PATH+ fileName;
        int currentLineNumber= 0;
        String splitString = ",";
        Map<Date, String> cookieMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(currentLineNumber != 0) {
                    String[] csvColumns = line.split(splitString);
                    String cookieName = csvColumns[0];
                    String cookieDateTime = csvColumns[1];
                    cookieMap.put(dateFormat.parse(cookieDateTime), cookieName);
                    logger.info(cookieName+","+cookieDateTime);
                }
                currentLineNumber++;
            }
        }

        return cookieMap;
    }
}