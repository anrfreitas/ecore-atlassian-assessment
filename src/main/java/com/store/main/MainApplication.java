package com.store.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

// Scanners
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.store.controllers.CustomerController;
import com.store.controllers.HelloController;
import com.store.controllers.OccurrenceController;
import com.store.repositories.CustomerRepository;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableConfigurationProperties
@EntityScan(basePackages = "com.store.entities")
@EnableJpaRepositories(basePackages = "com.store.repositories")
@ComponentScan({"com.store", "com.store.services"})
@SpringBootApplication(scanBasePackageClasses = { HelloController.class, CustomerController.class, OccurrenceController.class })
@EnableCaching
@EnableScheduling
public class MainApplication {

	@Autowired
    static CustomerRepository cRepository;

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}
}
