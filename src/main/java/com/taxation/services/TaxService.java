package com.taxation.services;

import com.taxation.dto.TaxDeductionDTO;
import com.taxation.entities.Employee;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class TaxService {

    private static final double TAX_RATE_1 = 0.05; // 5%
    private static final double TAX_RATE_2 = 0.10; // 10%
    private static final double TAX_RATE_3 = 0.20; // 20%
    private static final double CESS_RATE = 0.02; // 2%

    public TaxDeductionDTO calculateTax(Employee employee) {
        double yearlySalary = calculateYearlySalary(employee);
        double taxAmount = calculateTaxAmount(yearlySalary);
        double cessAmount = calculateCessAmount(yearlySalary);

        TaxDeductionDTO taxDeduction = new TaxDeductionDTO();
        taxDeduction.setEmployeeId(employee.getEmployeeId());
        taxDeduction.setFirstName(employee.getFirstName());
        taxDeduction.setLastName(employee.getLastName());
        taxDeduction.setYearlySalary(yearlySalary);
        taxDeduction.setTaxAmount(taxAmount);
        taxDeduction.setCessAmount(cessAmount);

        return taxDeduction;
    }

    private double calculateYearlySalary(Employee employee) {
        Date doj = employee.getDateOfJoining();
        Date currentDate = new Date(); // Assuming current date

        Calendar startOfFinancialYear = Calendar.getInstance();
        startOfFinancialYear.setTime(currentDate);
        startOfFinancialYear.set(Calendar.MONTH, Calendar.APRIL);
        startOfFinancialYear.set(Calendar.DAY_OF_MONTH, 1);
        startOfFinancialYear.set(Calendar.HOUR_OF_DAY, 0);
        startOfFinancialYear.set(Calendar.MINUTE, 0);
        startOfFinancialYear.set(Calendar.SECOND, 0);
        startOfFinancialYear.set(Calendar.MILLISECOND, 0);

        // If DOJ is after the start of the current financial year, consider it
        if (doj.after(startOfFinancialYear.getTime())) {
            startOfFinancialYear.add(Calendar.YEAR, -1);
        }

        Calendar endOfFinancialYear = (Calendar) startOfFinancialYear.clone();
        endOfFinancialYear.add(Calendar.YEAR, 1);

        // Calculate the number of months worked in the current financial year
        int monthsWorked = 0;
        if (doj.before(endOfFinancialYear.getTime())) {
            Calendar dojCalendar = Calendar.getInstance();
            dojCalendar.setTime(doj);
            int startYear = startOfFinancialYear.get(Calendar.YEAR);
            int endYear = endOfFinancialYear.get(Calendar.YEAR);
            int startMonth = startOfFinancialYear.get(Calendar.MONTH);
            int endMonth = endOfFinancialYear.get(Calendar.MONTH);
            int dojYear = dojCalendar.get(Calendar.YEAR);
            int dojMonth = dojCalendar.get(Calendar.MONTH);

            if (dojYear == startYear) {
                if (dojMonth >= startMonth) {
                    monthsWorked = 12 - dojMonth;
                }
            } else if (dojYear == endYear) {
                if (dojMonth <= endMonth) {
                    monthsWorked = dojMonth + 1;
                }
            } else {
                monthsWorked = 12;
            }
        }

        // Total salary = Salary per month * Number of months worked
        // Assuming constant salary per month
        return employee.getSalary() * monthsWorked;
    }

    private double calculateTaxAmount(double yearlySalary) {
        double taxAmount = 0;

        if (yearlySalary <= 250000) {
            // No tax
            taxAmount = 0;
        } else if (yearlySalary <= 500000) {
            // 5% Tax
            taxAmount = (yearlySalary - 250000) * TAX_RATE_1;
        } else if (yearlySalary <= 1000000) {
            // 10% Tax
            taxAmount = 12500 + (yearlySalary - 500000) * TAX_RATE_2;
        } else {
            // 20% Tax
            taxAmount = 62500 + (yearlySalary - 1000000) * TAX_RATE_3;
        }

        return taxAmount;
    }

    private double calculateCessAmount(double yearlySalary) {
        // Collect additional 2% cess for the amount more than 2500000
        if (yearlySalary > 2500000) {
            return (yearlySalary - 2500000) * CESS_RATE;
        } else {
            return 0;
        }
    }
}
