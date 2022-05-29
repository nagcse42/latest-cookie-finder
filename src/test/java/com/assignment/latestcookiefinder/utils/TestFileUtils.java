package com.assignment.latestcookiefinder.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class TestFileUtils {
    static Logger logger = LoggerFactory.getLogger(TestFileUtils.class);
    private static final String BASE_FILE_PATH = "src/main/resources/";

    public static void main(String[] args) {
        try {
            processCookieFile("Cookie_File.csv", "2018-12-09");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void processCookieFile(String fileName, String selectedDate) throws IOException, ParseException {
        logger.info("started for file {} and date", fileName, selectedDate);
        Map<Date, String> cookieMap = new HashMap<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        Date startDate = dateFormat.parse( selectedDate+ "T00:00:00+00:00");
        Date endDate = dateFormat.parse( selectedDate+ "T23:59:59+00:00");

        int currentLineNumber = 0;
        String splitString = ",";
        String csvFilePath = BASE_FILE_PATH+fileName;

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

        List<Map.Entry<String, LocalDate>> list = new ArrayList(cookieMap.keySet());
        List<Date> userSelectedDates = getDatesBetweenStartAndEndDate(cookieMap.keySet(), startDate, endDate);
//      ArrayList<Date> sortedKeys = new ArrayList<Date>(cookieMap.keySet());
        Collections.sort(userSelectedDates);
        logger.info("After sort");
//        sortedKeys.forEach(System.out::println);
//        Date latestDate = sortedKeys.get(sortedKeys.size()-1);
        userSelectedDates.forEach(date -> logger.info(date.toString()));
        Date latestDate = userSelectedDates.get(userSelectedDates.size()-1);
        logger.info("Latest Cookie -> "+cookieMap.get(latestDate) + " on "+latestDate);
    }

    private static List<Date> getDatesBetweenStartAndEndDate(Set<Date> dateList, Date startDate, Date endDate) throws ParseException {
        return dateList.stream()
                .filter(dates -> dates.after(startDate) && dates.before(endDate))
                .collect(Collectors.toList());
    }
}