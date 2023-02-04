package com.store.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.store.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("FROM Customer c ORDER BY c.id asc")
    List<Customer> findAll();

    @Query("SELECT count(c) FROM Customer c WHERE c.email=:email")
    Integer countEmailOccurrences(String email);
}