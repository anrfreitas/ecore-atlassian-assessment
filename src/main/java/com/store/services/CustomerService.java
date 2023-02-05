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
    private CustomerRepository cRepository;

    public List<CustomerSummaryTransformer> listAll() {
        return cRepository.findAll()
            .stream()
            .map(c -> new CustomerSummaryTransformer().toCustomerSummaryTransformer(c))
            .collect(Collectors.toList());
    }

    public Customer getById(Long id) {
        return cRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public void checkIfEmailExists(String email) {
        Integer count = cRepository.countEmailOccurrences(email);
        if (count > 0) throw new ConflictException();
    }

    public Customer save(Customer c) {
        return cRepository.save(c);
    }

    public Customer updateById(Long id, Customer c) {
        // Customer obj = this.getById(id);
        Customer obj = cRepository.findById(id).orElseThrow(NotFoundException::new);
        obj.setName(c.getName());
        return cRepository.save(obj);
    }

    public void deleteById(Long id) {
        Customer obj = this.getById(id);
        cRepository.delete(obj);
    }
}
