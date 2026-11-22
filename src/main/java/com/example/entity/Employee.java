package com.example.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

    @Entity
    @Table(name = "sundaynew")
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    @ToString
    public class Employee {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @NotBlank(message = "Employee name is mandatory")
        @Size(min = 3, max = 30, message = "Name must contain 3-30 characters")
        @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name must contain only alphabets and spaces")
        @Column(nullable = false, length = 30)
        private String name;

        @NotBlank(message = "email is mandatory")
        @Email(message = "Enter a valid email id")
        @Column(nullable = false, unique = true)
        private String email;

        @NotBlank(message = "Phone number is mandatory")
        @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Enter a valid 10-digit Indian mobile number")
        @Column(nullable = false, unique = true, length = 10)
        private String phone;

        @NotEmpty(message = "Dept cannot be empty")
        @Size(max = 20, message = "Dept Name must contain at most 20 characters")
        @Column(nullable = false, length = 20)
        private String department;

        @NotNull(message = "Age is must")
        @Min(value = 18, message = "Age must be at least 18")
        @Max(value = 65, message = "Age must not exceed 65")
        @Column(nullable = false)
        private Integer age;

        @NotNull(message = "Salary is mandatory")
        @Positive(message = "Salary must be a positive amount")
        @DecimalMin(value = "50000.00", inclusive = true, message = "Salary must be at least 50000")
        @Digits(integer = 10, fraction = 2, message = "Salary must be a valid monetary amount")
        @Column(nullable = false, precision = 12, scale = 2)
        private BigDecimal salary;

        @NotNull(message = "Date of birth is mandatory")
        @Past(message = "Date of birth must be in the past")
        @Column(nullable = false)
        private LocalDate dob;

        @NotNull(message = "Date of joining is mandatory")
        @FutureOrPresent(message = "Date of joining may be  in the future")
        @Column(nullable = false)
        private LocalDate doj;
    }

