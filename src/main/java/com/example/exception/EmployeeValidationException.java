package com.example.exception;

public class EmployeeValidationException extends RuntimeException {

    public EmployeeValidationException(String message) {
        super(message);
    }
}