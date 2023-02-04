package com.store.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

// Scanners
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.store.controllers.CustomerController;
import com.store.controllers.HelloController;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableConfigurationProperties
@EntityScan(basePackages = "com.store.entities")
@EnableJpaRepositories(basePackages = "com.store.repositories")
@ComponentScan({"com.store", "com.store.services"})
@SpringBootApplication(scanBasePackageClasses = {HelloController.class, CustomerController.class})
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

}
