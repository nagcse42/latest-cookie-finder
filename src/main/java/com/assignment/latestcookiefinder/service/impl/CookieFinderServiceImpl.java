package com.assignment.latestcookiefinder.service.impl;

import com.assignment.latestcookiefinder.service.CookieFinderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.assignment.latestcookiefinder.utils.FileUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class CookieFinderServiceImpl implements CookieFinderService {

    static Logger logger = LoggerFactory.getLogger(CookieFinderServiceImpl.class);

    private FileUtils fileUtils;

    public CookieFinderServiceImpl(FileUtils fileUtils) {
        this.fileUtils = fileUtils;
    }


    @Override
    public String findLatestCookieInDate(String fileName, String selectedDate) throws ParseException, IOException {
        logger.info("started for file {} and date", fileName, selectedDate);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        Map<Date, String> cookieMap = fileUtils.fetchCookieMapFromFile(fileName, dateFormat);

        List<Map.Entry<String, LocalDate>> list = new ArrayList(cookieMap.keySet());

        Date startDate = dateFormat.parse( selectedDate+ "T00:00:00+00:00");
        Date endDate = dateFormat.parse( selectedDate+ "T23:59:59+00:00");
        List<Date> userSelectedDates = getDatesBetweenStartAndEndDate(cookieMap.keySet(), startDate, endDate);
        if(userSelectedDates.isEmpty()) {
            logger.info("No cookies found for the date {}", selectedDate);
            return null;
        }
        Collections.sort(userSelectedDates);
        logger.info("After sort");
        userSelectedDates.forEach(date -> logger.info(date.toString()));
        Date latestDate = userSelectedDates.get(userSelectedDates.size()-1);
        logger.info("Latest Cookie -> "+cookieMap.get(latestDate) + " on "+latestDate);
        return cookieMap.get(latestDate);
    }

    private List<Date> getDatesBetweenStartAndEndDate(Set<Date> dateList, Date startDate, Date endDate) {
        return dateList.stream()
                .filter(dates -> dates.after(startDate) && dates.before(endDate))
                .collect(Collectors.toList());
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("File Name and Date in format like -f fileName -d YYYY-MM-DD");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        logger.info(line);
        String[] lineCommands = line.split("-f");
        if(lineCommands.length <= 1) {
            throw new Exception("Please enter file and date");
        }
        lineCommands = lineCommands[1].split("-d");
        if(lineCommands.length <= 1) {
            throw new Exception("Please enter file and date for the specific date");
        }
        String latestCookie = findLatestCookieInDate(lineCommands[0].trim(), lineCommands[1].trim());
        logger.info(latestCookie);
    }
}
