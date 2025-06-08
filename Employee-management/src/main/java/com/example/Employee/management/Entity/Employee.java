package com.example.Employee.management.Entity;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.*;

@Schema(
        description = "Employee Model Information"
)

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Employee ID", example = "1")
    private Long id;


    @Schema(
            description = "Employee Name"
    )
    @NotBlank(message = "Name is required")
    @Size(min = 3, message = "Name should have at least 3 characters")
    private String name;

    @Schema(
            description = "Employee Email"
    )
    @Email(message = "Invalid Email")
    @NotBlank(message = "Email is Required")
    private String email;

    @Schema(
            description = "Employee Department"
    )
    @NotBlank(message = "Department is Required")
    private String department;
}
