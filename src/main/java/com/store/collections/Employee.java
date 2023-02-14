package com.store.collections;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Employee {

    @Id
    String id;

    String name;

    Integer age;

    BigDecimal salary;

    @DBRef
    Employee boss;
}
