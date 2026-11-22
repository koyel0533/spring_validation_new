package com.example.service;

import com.example.entity.Employee;
import com.example.exception.EmployeeNotFoundException;
import com.example.exception.EmployeeValidationException;
import com.example.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    // CREATE
    public Employee saveEmployee(Employee employee) {
        validateDates(employee);

        boolean duplicateEmail = employeeRepository.findAll()
                .stream()
                .anyMatch(e -> e.getEmail().equalsIgnoreCase(employee.getEmail()));
        if (duplicateEmail) {
            throw new EmployeeValidationException("Email already exists: " + employee.getEmail());
        }

        boolean duplicatePhone = employeeRepository.findAll()
                .stream()
                .anyMatch(e -> e.getPhone().equals(employee.getPhone()));
        if (duplicatePhone) {
            throw new EmployeeValidationException("Phone number already exists: " + employee.getPhone());
        }

        return employeeRepository.save(employee);
    }

    // READ ALL
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // READ BY ID
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
    }

    // UPDATE
    public Employee updateEmployee(Long id, Employee employee) {
        Employee existing = getEmployeeById(id);
        validateDates(employee);

        boolean duplicateEmail = employeeRepository.findAll()
                .stream()
                .anyMatch(e -> e.getEmail().equalsIgnoreCase(employee.getEmail()) && !e.getId().equals(id));
        if (duplicateEmail) {
            throw new EmployeeValidationException("Email already exists: " + employee.getEmail());
        }

        boolean duplicatePhone = employeeRepository.findAll()
                .stream()
                .anyMatch(e -> e.getPhone().equals(employee.getPhone()) && !e.getId().equals(id));
        if (duplicatePhone) {
            throw new EmployeeValidationException("Phone number already exists: " + employee.getPhone());
        }

        existing.setName(employee.getName());
        existing.setEmail(employee.getEmail());
        existing.setPhone(employee.getPhone());
        existing.setDepartment(employee.getDepartment());
        existing.setAge(employee.getAge());
        existing.setSalary(employee.getSalary());
        existing.setDob(employee.getDob());
        existing.setDoj(employee.getDoj());

        return employeeRepository.save(existing);
    }

    // DELETE
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }

    private void validateDates(Employee employee) {
        if (employee.getDob() != null && employee.getDoj() != null) {
            if (!employee.getDoj().isAfter(employee.getDob())) {
                throw new EmployeeValidationException("Date of joining must be after date of birth");
            }
            if (Period.between(employee.getDob(), employee.getDoj()).getYears() < 18) {
                throw new EmployeeValidationException("Employee must be at least 18 years old at joining");
            }
        }
    }
}