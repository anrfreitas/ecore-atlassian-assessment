package com.store.services.beans;

import org.springframework.stereotype.Component;

@Component
public class EagerBean {

    public EagerBean() {
        System.out.println("EagerBean loaded!");
    }
}
