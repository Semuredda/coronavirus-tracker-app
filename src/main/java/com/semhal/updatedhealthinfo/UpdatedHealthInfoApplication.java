package com.semhal.updatedhealthinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UpdatedHealthInfoApplication {

	public static void main(String[] args) {
		SpringApplication.run(UpdatedHealthInfoApplication.class, args);
	}

}
