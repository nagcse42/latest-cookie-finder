package com.assignment.latestcookiefinder;

import com.assignment.latestcookiefinder.service.CookieFinderService;
import com.assignment.latestcookiefinder.service.impl.CookieFinderServiceImpl;
import com.assignment.latestcookiefinder.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LatestCookieFinderApplication {
	static Logger logger = LoggerFactory.getLogger(LatestCookieFinderApplication.class);

	public static void main(String[] args) {
		logger.info("Application started");
		SpringApplication.run(LatestCookieFinderApplication.class, args);
		cookieFinderService();
	}

	@Bean
	public static CookieFinderService cookieFinderService() {
		return new CookieFinderServiceImpl(new FileUtils());
	}
}
