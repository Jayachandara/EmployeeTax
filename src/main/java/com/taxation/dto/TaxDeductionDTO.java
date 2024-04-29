package com.taxation.dto;

import lombok.Data;

@Data
public class TaxDeductionDTO {
    private Long employeeId;
    private String firstName;
    private String lastName;
    private double yearlySalary;
    private double taxAmount;
    private double cessAmount;
}
