package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;

/**
 * Employee
 *
 * A domain model representing a single, already-validated and normalized
 * employee payroll record.
 *
 * The five "input" fields (employeeId, name, department, hoursWorked,
 * hourlyRate) are set once at construction time and never change -
 * by the time an Employee object exists, it has already passed
 * validation in EmployeeRowParser. The three "derived" fields (grossPay,
 * payLevel, employmentStatus) start out unset and are filled in later by
 * PayrollCalculator, once per Employee, during the Transform stage.
 *
 * Employee also knows how to represent itself as a single output CSV
 * row (toCsvRow()) - keeping output-formatting logic next to the data
 * it formats, rather than scattered through the class that does the
 * file writing.
 */
public class Employee {

    private final int employeeId;
    private final String name;
    private final String department;
    private final double hoursWorked;
    private final double hourlyRate;

    // Derived fields - populated later by PayrollCalculator.
    private BigDecimal grossPay;
    private String payLevel;
    private String employmentStatus;

    public Employee(int employeeId, String name, String department, double hoursWorked, double hourlyRate) {
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public BigDecimal getGrossPay() {
        return grossPay;
    }

    public String getPayLevel() {
        return payLevel;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    /**
     * Records the results of the payroll calculation for this employee.
     * Called exactly once per employee, by PayrollCalculator, during the
     * Transform stage.
     */
    public void setPayrollResults(BigDecimal grossPay, String payLevel, String employmentStatus) {
        this.grossPay = grossPay;
        this.payLevel = payLevel;
        this.employmentStatus = employmentStatus;
    }

    /**
     * Formats this employee as one line of the output CSV, in the
     * required column order. HoursWorked and HourlyRate are formatted to
     * two decimal places for display only; the underlying values used
     * for payroll math are unaffected.
     *
     * Must only be called after setPayrollResults() has been invoked.
     */
    public String toCsvRow() {
        return String.join(",",
                String.valueOf(employeeId),
                name,
                department,
                String.format("%.2f", hoursWorked),
                String.format("%.2f", hourlyRate),
                grossPay.toPlainString(),
                payLevel,
                employmentStatus);
    }
}
