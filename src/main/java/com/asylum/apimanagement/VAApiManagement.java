package com.asylum.apimanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) // remove exclude to enable security
public class VAApiManagement {

	public static void main(String[] args) {
		SpringApplication.run(VAApiManagement.class, args);
	}

}
