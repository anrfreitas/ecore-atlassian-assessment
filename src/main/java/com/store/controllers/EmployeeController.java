package com.store.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.store.collections.Employee;
import com.store.services.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAll() {
        return this.employeeService.getAll();
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable String id) {
        return this.employeeService.getById(id);
    }

    @GetMapping("/range")
    public List<Employee> getByAge(@RequestParam Integer from, @RequestParam Integer to) {
        return this.employeeService.getByAge(from, to);
    }

    @GetMapping("/name")
    public List<Employee> getByName(@RequestParam String name) {
        return this.employeeService.getByName(name);
    }

    @PostMapping
    public Employee saveNew(@RequestBody Employee employee) {
        return this.employeeService.saveNew(employee);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        this.employeeService.deleteById(id);
    }
}
