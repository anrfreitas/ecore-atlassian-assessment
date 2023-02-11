package com.store.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.store.entities.Customer;
import com.store.repositories.CustomerRepository;

@Service
public class ThreadingService {

    @Autowired
    private CustomerRepository customerRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    @Async
    public CompletableFuture<List<Customer>> saveCustomer (final InputStream fileStream) throws Exception {

        final long start = System.currentTimeMillis();

        List<Customer> customers = this.parseCSVFile(fileStream);

        LOGGER.info("Saving a list of customers of size {} records - Thread: " + Thread.currentThread().getName(), customers.size());

        customers = customerRepository.saveAll(customers);

        LOGGER.info("Elapsed time: {}", (System.currentTimeMillis() - start));

        return CompletableFuture.completedFuture(customers);

    }

    @Async
    public CompletableFuture<List<Customer>> getAllCustomers() throws InterruptedException {

        LOGGER.info("Request to get a list of customers - Thread: " + Thread.currentThread().getName());
        Thread.sleep(1000L);

        final List<Customer> customers = customerRepository.findAll();
        return CompletableFuture.completedFuture(customers);
    }

    private List<Customer> parseCSVFile(final InputStream fileStream) throws Exception {
        final List<Customer> customers = new ArrayList<>();
        try {
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(fileStream))) {
                String line;
                while ((line=br.readLine()) != null) {
                    final String[] data=line.split(",");
                    final Customer customer = new Customer();
                    customer.setName(data[0]);
                    customer.setEmail(data[1]);
                    customers.add(customer);
                }

                return customers;
            }
        } catch(final IOException e) {
            LOGGER.error("Failed to parse CSV file {}", e);
            throw new Exception("Failed to parse CSV file {}", e);
        }
    }


}
