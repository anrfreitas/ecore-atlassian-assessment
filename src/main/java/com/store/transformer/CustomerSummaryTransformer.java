package com.store.transformer;

import com.store.entities.Customer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerSummaryTransformer {
    private Long Id;
    private String name;
    private String email;

    public CustomerSummaryTransformer toCustomerSummaryTransformer(Customer customer) {
        CustomerSummaryTransformer t = new CustomerSummaryTransformer();
        t.setId(customer.getId());
        t.setName(customer.getName());
        t.setEmail(customer.getEmail());
        return t;
    }
}
