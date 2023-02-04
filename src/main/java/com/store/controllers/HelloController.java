package com.store.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.dto.HelloWorldDTO;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    Flyway flyway;

    @GetMapping("/world")
    public String getTest() {
        this.resetDatabase();
        return "It works! And the database was resetted too!";
    }

    @PostMapping("/world")
    public HelloWorldDTO postTest(@Valid @RequestBody(required = true) HelloWorldDTO payload) {
        // Validating @RequestBody
        return payload;
    }

    @PutMapping("/world")
    public Map<String, Object> putTest(@RequestBody Map<String, Object> payload) {
        // Not validating @RequestBody
        return payload;
    }

    private void resetDatabase() {
        // It would be useful to use before Each execution on an unit tests file!
        flyway.clean(); // Cleaning database
        flyway.migrate(); // Migrating stuff
    }
}
