package com.example.Employee.management.Repository;

import com.example.Employee.management.Entity.Employee;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Tag(name = "Database connection from mysql to spring")
public interface EmployeeRepository extends JpaRepository<Employee,Long> {


    Optional<Employee> findByEmail(String email);
}
