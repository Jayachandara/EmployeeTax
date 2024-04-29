package com.taxation.dto;

import lombok.Data;

import java.util.Date;

@Data
public class EmployeeDTO {

    private Long employeeId;

    private String firstName;

    private String lastName;

    private Date dateOfJoining;

    private Double salary;

    private String email;

    private String phoneNumbers;
}
