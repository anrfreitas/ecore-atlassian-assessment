package com.store.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.entities.Customer;
import com.store.entities.Telephone;
import com.store.exceptions.NotFoundException;
import com.store.repositories.CustomerRepository;
import com.store.repositories.TelephoneRepository;

@Service
public class TelephoneService {

    @Autowired
    private CustomerRepository cRepository;

    @Autowired
    private TelephoneRepository tRepository;

    public void addTelephone(Long customerId, Telephone telephone) {
        Customer c = cRepository.findById(customerId).orElseThrow(NotFoundException::new);
        telephone.setCustomer(c);
        tRepository.save(telephone);
    }
}
