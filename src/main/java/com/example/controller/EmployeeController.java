package com.example.controller;

import com.example.entity.Employee;
import com.example.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> addEmployee(@Valid @RequestBody Employee employee)
    {
        return new ResponseEntity<>(employeeService.saveEmployee(employee), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Employee>> allEmployee()
    {
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public Employee employeeId(@PathVariable Long id)
    {
        return employeeService.getEmployeeById(id);

    }


    @PutMapping("{id}")
    public ResponseEntity<Employee>  updateEmployee(@PathVariable Long id, @RequestBody Employee employee)
    {
        return new ResponseEntity<>(employeeService.updateEmployee(id,employee),HttpStatus.OK);
    }



}
