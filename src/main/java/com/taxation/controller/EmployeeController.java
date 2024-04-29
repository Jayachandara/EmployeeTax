package com.taxation.controller;

import com.taxation.dto.EmployeeDTO;
import com.taxation.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDTO employeeDTO) throws ValidationException{
        try {
            if(employeeDTO.getFirstName() == null || employeeDTO.getFirstName().isBlank()){
                throw new ValidationException("First name is required");
            }

            if(employeeDTO.getLastName() == null || employeeDTO.getLastName().isBlank()){
                throw new ValidationException("Last name is required");
            }

            if(employeeDTO.getSalary() == null || employeeDTO.getSalary() < 0){
                throw new ValidationException("Salary is required and must be positive");
            }

            if(employeeDTO.getDateOfJoining() == null){
                throw new ValidationException("Date of joining required");
            }

            EmployeeDTO createdEmployee = employeeService.createEmployee(employeeDTO);
            return ResponseEntity.ok(createdEmployee);
        } catch (ValidationException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
