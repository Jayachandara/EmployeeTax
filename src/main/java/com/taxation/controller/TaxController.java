package com.taxation.controller;

import com.taxation.dto.TaxDeductionDTO;
import com.taxation.entities.Employee;
import com.taxation.services.EmployeeService;
import com.taxation.services.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/tax")
public class TaxController {

    @Autowired
    TaxService taxService;

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/deductions/{employeeId}")
    public ResponseEntity<TaxDeductionDTO> getEmployeeTaxDeduction(@PathVariable Long employeeId) {

        Optional<Employee> employee = employeeService.getEmployeeById(employeeId); // You need to implement this method

        if(employee.isPresent()){
            TaxDeductionDTO taxDeduction = taxService.calculateTax(employee.get());
            return ResponseEntity.ok(taxDeduction);
        }else {
            throw new RuntimeException("Employee not found");
        }
    }
}
