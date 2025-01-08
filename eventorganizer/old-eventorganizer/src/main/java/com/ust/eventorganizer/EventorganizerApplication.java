package com.ust.eventorganizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EventorganizerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventorganizerApplication.class, args);
	}

}
