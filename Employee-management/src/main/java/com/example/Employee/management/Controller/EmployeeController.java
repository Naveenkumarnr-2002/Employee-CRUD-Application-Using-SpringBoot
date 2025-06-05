package com.example.Employee.management.Controller;


import com.example.Employee.management.Entity.Employee;
import com.example.Employee.management.Repository.EmployeeRepository;
import com.example.Employee.management.Service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Tag(name = "Employee Controller", description = "CRUD operations and view pages for employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/")
    public String viewHome(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "index";
    }

    @GetMapping("/employee/add")
    public String addEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "add";
    }

    @PostMapping("/employee/save")
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult result, Model model) {

        Optional<Employee>existing=employeeRepository.findByEmail(employee.getEmail());

        if (existing.isPresent()) {
            result.rejectValue("email", "error.employee", "Email already exists");
        }

        if (result.hasErrors()) {
            return "add";
        }
        employeeRepository.save(employee);
        return "redirect:/";
    }

    @GetMapping("/employee/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeService.getEmployeeById(id));
        return "edit";
    }

    @PostMapping("/employee/update")
    public String updateEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult result,Model model) {

        Optional<Employee>existing=employeeRepository.findByEmail(employee.getEmail());

        if (existing.isPresent() && !existing.get().getId().equals(employee.getId())) {
            result.rejectValue("email", "error.employee", "Email already exists");
        }
        if (result.hasErrors()) {
            return "edit";
        }
        employeeService.saveEmployee(employee);
        return "redirect:/";
    }

    @GetMapping("/employee/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/";
    }
    @GetMapping("/employee/view/{id}")
    public String viewEmployee(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeService.getEmployeeById(id));
        return "view";
    }



    // REST endpoints

    @Operation(summary = "Get all employees")
    @ResponseBody
    @GetMapping("/api/employees")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @Operation(summary = "Get single employee")
    @ResponseBody
    @GetMapping("/api/employees/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }









}























