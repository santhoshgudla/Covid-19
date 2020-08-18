package com.covid.stats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CovidStatsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CovidStatsServiceApplication.class, args);
	}

}
