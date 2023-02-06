package com.store.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.store.entities.Customer;
import com.store.entities.Telephone;
import com.store.services.CustomerService;
import com.store.services.TelephoneService;
import com.store.transformer.CustomerSummaryTransformer;

@RestController
@Validated
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TelephoneService telephoneService;

    @GetMapping(
        value = "",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerSummaryTransformer> list() {
        return customerService.listAll();
    }

    @GetMapping(
        value = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public Customer get(@PathVariable("id") Long id) {
        return customerService.getById(id);
    }

    @PostMapping(
        value = "",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Customer save(@Valid @RequestBody(required = true) Customer body) {
        customerService.checkIfEmailExists(body.getEmail());
        return customerService.save(body);

    }

    @PutMapping(
        value = "/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public Customer update(@PathVariable("id") Long id, @RequestBody(required = true) Customer body) {
        return customerService.updateById(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        customerService.deleteById(id);
    }

    @PostMapping(
        value = "/{customerId}/telephone",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void addTelephone(
        @PathVariable("customerId") Long customerId,
        @Valid @RequestBody(required = true) Telephone telephone
    ) {
        telephoneService.addTelephone(customerId, telephone);
    }
}
