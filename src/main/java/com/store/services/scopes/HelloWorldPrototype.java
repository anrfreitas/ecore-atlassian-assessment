package com.store.services.scopes;

import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class HelloWorldPrototype {

    private String dateTime = new Date(System.currentTimeMillis()).toString();

    public String getScopedValue() {
        return "HelloWorldPrototype - " + dateTime;
    }
}
