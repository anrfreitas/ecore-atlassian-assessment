package com.store.services.scopes;

import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class HelloWorldSingleton {

    private String dateTime = new Date(System.currentTimeMillis()).toString();

    public String getScopedValue() {
        return "HelloWorldSingleton - " + dateTime;
    }
}
