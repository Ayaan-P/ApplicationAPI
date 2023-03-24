package com.asylum.apimanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // (exclude = { SecurityAutoConfiguration.class }) add exclude to disable security
public class VAApiManagement {

	public static void main(String[] args) {
		SpringApplication.run(VAApiManagement.class, args);
	}

}
