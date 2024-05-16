package com.home;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.home"})
public class HappyHouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(HappyHouseApplication.class, args);
	}

}
