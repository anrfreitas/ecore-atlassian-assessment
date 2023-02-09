package com.store.configs.profiles;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("prod")
@Configuration
public class ProdBeanConfig implements EnvironmentInterface {

    @Bean
    public void loadProfile() {
        System.out.println("Prod Profile Loaded!");
    }

    @Bean
    public String getEnvironment() {
        return "prod-profile";
    }

    @Bean
    public String getHelloWorldMessage() {
        return "Hello World Prod";
    }

    @Bean(value = "itWorksMessage")
    public String getItWorksMessage() {
        return "It works Prod!";
    }
}
