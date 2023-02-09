package com.store.configs.profiles;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@Configuration
public class DevBeanConfig implements EnvironmentInterface{

    @Bean
    public void loadProfile() {
        System.out.println("Dev Profile Loaded!");
    }

    @Bean
    public String getEnvironment() {
        return "dev-profile";
    }

    @Bean
    public String getHelloWorldMessage() {
        return "Hello World Dev";
    }

    @Bean(value = "itWorksMessage")
    public String getItWorksMessage() {
        return "It works!";
    }
}
