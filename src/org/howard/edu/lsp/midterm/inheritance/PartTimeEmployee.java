package org.howard.edu.lsp.midterm.inheritance;

/**
 * A part-time employee, paid straight hourly pay for all hours worked.
 *
 * @author Noah
 */
public class PartTimeEmployee extends Employee {

    /**
     * Creates a part-time employee.
     *
     * @param hoursWorked the hours worked in the week (nonnegative)
     * @param hourlyRate  the normal hourly rate (nonnegative)
     */
    public PartTimeEmployee(double hoursWorked, double hourlyRate) {
        super(hoursWorked, hourlyRate);
    }

    /**
     * Calculates straight hourly pay.
     *
     * @return hours worked times hourly rate
     */
    @Override
    public double calculatePay() {
        return getHoursWorked() * getHourlyRate();
    }
}
