package com.taxation.services;

import com.taxation.dto.EmployeeDTO;
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


    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        // Convert DTO to entity
        Employee employee = convertToEntity(employeeDTO);

        // Save employee entity
        Employee savedEmployee = employeeRepository.save(employee);

        // Convert entity to DTO and return
        return convertToDTO(savedEmployee);
    }

    private Employee convertToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setPhoneNumber(employeeDTO.getPhoneNumbers());
        employee.setDateOfJoining(employeeDTO.getDateOfJoining());
        employee.setSalary(employeeDTO.getSalary());
        return employee;
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(employee.getEmployeeId());
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setLastName(employee.getLastName());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setPhoneNumbers(employee.getPhoneNumber());
        employeeDTO.setDateOfJoining(employee.getDateOfJoining());
        employeeDTO.setSalary(employee.getSalary());
        return employeeDTO;
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
