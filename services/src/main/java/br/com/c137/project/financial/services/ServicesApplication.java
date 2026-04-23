package br.com.c137.project.financial.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ServicesApplication {

	static void main(String[] args) {
		SpringApplication.run(ServicesApplication.class, args);
	}

}
