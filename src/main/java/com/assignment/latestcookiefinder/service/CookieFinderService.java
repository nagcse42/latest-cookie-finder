package com.assignment.latestcookiefinder.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;

@Service
public interface CookieFinderService extends CommandLineRunner {
    String findLatestCookieInDate(String fileName, String cookieDate) throws ParseException, IOException;
}
