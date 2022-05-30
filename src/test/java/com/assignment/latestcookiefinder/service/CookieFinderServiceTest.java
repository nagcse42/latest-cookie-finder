package com.assignment.latestcookiefinder.service;


import com.assignment.latestcookiefinder.service.impl.CookieFinderServiceImpl;
import com.assignment.latestcookiefinder.utils.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Scanner;

public class CookieFinderServiceTest {

    private CookieFinderService cookieFinder;

    Scanner scanner;

    @Test
    public void test_findLatestCookieInDate_exception() {
        cookieFinder = new CookieFinderServiceImpl(null);
        Assertions.assertThrows(NullPointerException.class,
                () -> cookieFinder.findLatestCookieInDate("Cookie_File.csv", "2018-12-09"));
    }

    @Test
    public void test_findLatestCookieInDate() throws IOException, ParseException {
        cookieFinder = new CookieFinderServiceImpl(new FileUtils());
        String latestCookie = cookieFinder.findLatestCookieInDate("Cookie_File.csv", "2018-12-09");
        Assertions.assertNotNull(latestCookie);
        Assertions.assertEquals("AtY0laUfhglK3lC7", latestCookie);
    }

    @Test
    public void test_findLatestCookieInDate_Null() throws IOException, ParseException {
        cookieFinder = new CookieFinderServiceImpl(new FileUtils());
        String latestCookie = cookieFinder.findLatestCookieInDate("Cookie_File.csv", "2022-12-09");
        Assertions.assertNull(latestCookie);
    }

    @Test
    public void test_findLatestCookieInDate_EmptyFile() throws IOException, ParseException {
        cookieFinder = new CookieFinderServiceImpl(new FileUtils());
        String latestCookie = cookieFinder.findLatestCookieInDate("Cookie_File_Empty.csv", "2022-12-09");
        Assertions.assertNull(latestCookie);
    }

    @Test
    public void test_findLatestCookieInDate_2022File() throws IOException, ParseException {
        cookieFinder = new CookieFinderServiceImpl(new FileUtils());
        String latestCookie = cookieFinder.findLatestCookieInDate("Cookie_File_2022.csv", "2022-01-09");
        Assertions.assertNotNull(latestCookie);
        Assertions.assertEquals("5UAVanZf6UtGyKVS", latestCookie);
        latestCookie = cookieFinder.findLatestCookieInDate("Cookie_File_2022.csv", "2022-12-08");
        Assertions.assertNotNull(latestCookie);
        Assertions.assertEquals("SAZuXPGUrfbcn5UA", latestCookie);
    }

    @Test
    public void test_run() throws Exception {
        cookieFinder = new CookieFinderServiceImpl(new FileUtils());
        scanner = new Scanner(System.in);
        String input = "add 5";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        try {
            cookieFinder.run("Test");
        }catch (Exception e) {
            Assertions.assertEquals("Please enter file and date", e.getMessage());
        }

        input = "-f Cookie_File.csv";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        try {
            cookieFinder.run("");
        }catch (Exception e) {
            Assertions.assertEquals("Please enter file and date for the specific date", e.getMessage());
        }

        input = "-f Cookie_File.csv -d 2018-12-09";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        cookieFinder.run("");
    }
}
