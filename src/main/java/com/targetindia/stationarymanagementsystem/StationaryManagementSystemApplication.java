t package com.targetindia.stationarymanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class StationaryManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(StationaryManagementSystemApplication.class, args);
	}

}
