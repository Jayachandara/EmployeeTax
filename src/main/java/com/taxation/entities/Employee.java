package com.taxation.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;// Multiple or single with comma

    private Date dateOfJoining;

    private Double salary;

    public Employee(String firstName, String lastName, String email, String phoneNumber, Date dateOfJoining, Double salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfJoining = dateOfJoining;
        this.salary = salary;
    }
}
