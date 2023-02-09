package com.store.services.implementations;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class HelloWorldServiceV1 implements HelloWorldServiceInterface {

    public String getMessage() {
        return "Hello World V1!";
    }
}
