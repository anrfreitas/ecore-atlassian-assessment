package com.store.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    public List<CustomerSummaryTransformer> listAll() {
        return cRepository.findAll()
            .stream()
            .map(c -> new CustomerSummaryTransformer().toCustomerSummaryTransformer(c))
            .collect(Collectors.toList());
    }

    // @description: run once, then show only cached value
    @Cacheable(cacheNames = "customers", key = "#id")
    public Customer getById(Long id) {
        this.printCurrentTimestamp(); // implemented this function to test caching system
        return cRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public void checkIfEmailExists(String email) {
        Integer count = cRepository.countEmailOccurrences(email);
        if (count > 0) throw new ConflictException();
    }

    public Customer save(Customer c) {
        return cRepository.save(c);
    }

    // @description: will actually run the method and update cache content
    @CachePut(cacheNames = "customers", key = "#id")
    public Customer updateById(Long id, Customer c) {
        // Customer obj = this.getById(id);
        Customer obj = cRepository.findById(id).orElseThrow(NotFoundException::new);
        obj.setName(c.getName());
        return cRepository.save(obj);
    }

    // @description: if ran, will erase the cache entry
    @CacheEvict(cacheNames = "customers", key = "#id")
    public void deleteById(Long id) {
        Customer obj = this.getById(id);
        cRepository.delete(obj);
    }

    /*
        @description: cron defined to clean up cache after 15 seconds
        the solution could've done using something else too
        but I really wanted to put up a cronjob :)
     */
    @CacheEvict(cacheNames = "customers", allEntries = true)
    @Scheduled(fixedDelay = 1000 * 15)
    public void emptyCustomersCache() {
        LOGGER.info("Emptying Customers cache...");
    }

    private void printCurrentTimestamp() {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        LOGGER.info(formatter.format(date));
    }
}
