package com.project.apivenda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ApivendaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApivendaApplication.class, args);
	}

}
