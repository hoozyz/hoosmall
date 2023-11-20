package com.hoozy.hoosshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.log4j.Log4j2;

@EnableScheduling
@SpringBootApplication
public class HoosshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(HoosshopApplication.class, args);
	}

}
