package com.store.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.entities.Customer;
import com.store.exceptions.ConflictException;
import com.store.exceptions.NotFoundException;
import com.store.repositories.CustomerRepository;
import com.store.transformer.CustomerSummaryTransformer;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public List<CustomerSummaryTransformer> listAll() {
        return repository.findAll()
            .stream()
            .map(c -> new CustomerSummaryTransformer().toCustomerSummaryTransformer(c))
            .collect(Collectors.toList());
    }

    public Customer getById(Long id) {
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }

    public void checkIfEmailExists(String email) {
        Integer count = repository.countEmailOccurrences(email);
        if (count > 0) throw new ConflictException();
    }

    public Customer save(Customer c) {
        return repository.save(c);
    }

    public Customer updateById(Long id, Customer c) {
        Customer obj = this.getById(id);
        obj.setName(c.getName());
        return repository.save(obj);
    }

    public void deleteById(Long id) {
        Customer obj = this.getById(id);
        repository.delete(obj);
    }
}
