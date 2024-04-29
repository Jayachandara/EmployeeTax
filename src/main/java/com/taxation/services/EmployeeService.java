package com.taxation.services;

import com.taxation.entities.Employee;
import com.taxation.repository.EmployeeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @PostConstruct
    public void init() {
        // Create some dummy employee data
        List<Employee> employees = Arrays.asList(
                new Employee("Jhansi", "Rani", "jhansi.rani@gmail.com", "1234567890", getDate(2023, 5, 15), 50000.00),
                new Employee("Ganesh", "Rao", "ganesh.rao@gmail.com", "0987654321", getDate(2023, 5, 15), 60000.00),
                new Employee("Jayachandra", "Nandana", "jayachandara.rao@gmail.com", "8331961238", getDate(2023, 4, 1), 62500.00),
                new Employee("Thanuja", "Kanthi", "thanuja.kanthi@gmail.com", "0987654322", getDate(2023, 4, 1), 235000.00)

        );

        // Save dummy employees to the database
        employeeRepository.saveAll(employees);
    }

    private Date getDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day); // Note: Month is 0-based in Calendar
        return calendar.getTime();
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long employeeID) {
        return employeeRepository.findById(employeeID);
    }
}
