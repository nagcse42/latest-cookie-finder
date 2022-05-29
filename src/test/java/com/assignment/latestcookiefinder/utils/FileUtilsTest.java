package com.assignment.latestcookiefinder.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class FileUtilsTest {
    FileUtils fileUtils;
    DateFormat dateFormat;

    @Test
    public void test_FileReading() throws IOException, ParseException {
        fileUtils = new FileUtils();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        Map<Date, String> map = fileUtils.fetchCookieMapFromFile("Cookie_File.csv", dateFormat);
        Assertions.assertNotNull(map);
        Assertions.assertTrue(map.size() > 0);
    }

    @Test
    public void test_FileReading_IOException() {
        fileUtils = new FileUtils();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        Assertions.assertThrows(IOException.class,
                () -> fileUtils.fetchCookieMapFromFile("Cookie_File111.csv", dateFormat));
    }

    @Test
    public void test_stringSplit() {
        String str = "-f Cookie_file.csv -d 2022-12-12";
        String[] strs = str.split("-f");
        strs = strs[1].split("-d");
        System.out.print(strs[0]+" - "+strs[1]);
    }
}
