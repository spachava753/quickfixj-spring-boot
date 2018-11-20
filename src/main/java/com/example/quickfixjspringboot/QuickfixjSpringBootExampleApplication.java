package com.example.quickfixjspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class QuickfixjSpringBootExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickfixjSpringBootExampleApplication.class, args);
	}
}
