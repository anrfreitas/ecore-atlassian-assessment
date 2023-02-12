package com.store.services.implementations;

import org.springframework.stereotype.Service;

@Service
public class HelloWorldServiceV2 implements HelloWorldService {

    public String getMessage() {
        return "Hello World V2!";
    }
}
