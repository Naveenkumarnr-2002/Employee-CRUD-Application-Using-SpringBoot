package com.example.Employee.management.Config;



import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.*;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI employeeManagementOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Employee Management REST API")
                        .description("REST API documentation for managing employees")
                        .version("1.0.0")
                        .contact(new Contact().name("Naveen kumar").email("naveen@example.com")));
    }
}
