package com.store.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.collections.Employee;
import com.store.repositories.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Employee getById(String id) {
        return employeeRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Employee does not exist"));
    }

    public List<Employee> getByAge(Integer from, Integer to) {
        return employeeRepository.getEmployeesByAge(from, to);
    }

    public List<Employee> getByName(String name) {
        return employeeRepository.getEmployeesByName(name);
    }

    public Employee saveNew(Employee employee) {
        if (employee.getBoss() != null) {
            Employee boss = employeeRepository
            .findById(employee.getBoss().getId())
            .orElseThrow(() -> new IllegalArgumentException("Employee does not exist"));

            employee.setBoss(boss);
        }

        return employeeRepository.save(employee);
    }

    public void deleteById(String id) {
        employeeRepository.deleteById(id);
    }
}
