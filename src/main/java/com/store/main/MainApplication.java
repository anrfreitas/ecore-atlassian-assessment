package com.store.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

// Scanners
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableConfigurationProperties
@EntityScan(basePackages = "com.store.entities")
@EnableJpaRepositories(basePackages = "com.store.repositories")
@EnableMongoRepositories(basePackages = "com.store.repositories")
@ComponentScan({"com.store", "com.store.services"})
@SpringBootApplication(scanBasePackages = "com.store.controllers")
@EnableCaching
@EnableScheduling
public class MainApplication {
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}
}
