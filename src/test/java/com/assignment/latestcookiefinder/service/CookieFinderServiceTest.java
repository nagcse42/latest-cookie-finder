package com.assignment.latestcookiefinder.service;


import com.assignment.latestcookiefinder.service.impl.CookieFinderServiceImpl;
import com.assignment.latestcookiefinder.utils.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.text.ParseException;

public class CookieFinderServiceTest {
    private CookieFinderService cookieFinder;

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
}
