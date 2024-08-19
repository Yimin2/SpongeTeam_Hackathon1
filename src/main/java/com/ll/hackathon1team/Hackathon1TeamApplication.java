package com.ll.hackathon1team;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Hackathon1TeamApplication {

	public static void main(String[] args) {
		SpringApplication.run(Hackathon1TeamApplication.class, args);
	}

}
