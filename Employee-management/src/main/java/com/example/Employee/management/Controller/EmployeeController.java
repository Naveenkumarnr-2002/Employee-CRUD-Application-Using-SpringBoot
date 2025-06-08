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

@Tag(

        name = "Employee Management CRUD Application",
        description = "CRUD REST APIs - Create User, Update User, Get User, Get All Users, Delete User"

)
@Controller
@RequiredArgsConstructor
@Tag(name = "Employee Controller", description = "CRUD operations and view pages for employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Operation(
            summary = "The main page of the website displays all the user data's from the DataBase."

    )
    @GetMapping("/")
    public String viewHome(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "index";
    }

    @Operation(
            summary = "Get details from user by using the add form"
    )
    @GetMapping("/employee/add")
    public String addEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "add";
    }

    @Operation(
            summary = "Save user details",
            description = "Save the Employee Details and check the employee Email was not used by any other user if another user used it says That Email was used"
    )
    @PostMapping("/employee/save")
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult result, Model model) {

        Optional<Employee> existing = employeeRepository.findByEmail(employee.getEmail());

        if (existing.isPresent()) {
            result.rejectValue("email", "error.employee", "Email already exists");
        }

        if (result.hasErrors()) {
            return "add";
        }
        employeeRepository.save(employee);
        return "redirect:/";
    }


    @Operation(
            summary = "Allowing user for update data if any needed"
    )

    @GetMapping("/employee/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeService.getEmployeeById(id));
        return "edit";
    }

    @Operation(
            summary = "Save the new details and also check the if the user given email used by another user or not"
    )
    @PostMapping("/employee/update")
    public String updateEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult result, Model model) {

        Optional<Employee> existing = employeeRepository.findByEmail(employee.getEmail());

        if (existing.isPresent() && !existing.get().getId().equals(employee.getId())) {
            result.rejectValue("email", "error.employee", "Email already exists");
        }
        if (result.hasErrors()) {
            return "edit";
        }
        employeeService.saveEmployee(employee);
        return "redirect:/";
    }
    @Operation(
            summary = "Delete employee by id",
            description ="Deleting a specific employee from the database using their employee Id."
    )
    @GetMapping("/employee/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/";
    }

    @Operation(
            summary = "View details of a user using their employee ID"

    )
    @GetMapping("/employee/view/{id}")
    public String viewEmployee(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeService.getEmployeeById(id));
        return "view";
    }
}
