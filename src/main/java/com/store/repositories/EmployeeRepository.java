package com.store.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.store.collections.Employee;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

    @Query("{ $and: [ { 'age': { $gte: ?0 } }, { 'age': { $lte: ?1 } } ] }")
    public List<Employee> getEmployeesByAge(Integer from, Integer to);

    @Query("{ 'name': /.*?0.*/ }")
    public List<Employee> getEmployeesByName(String name);
}
