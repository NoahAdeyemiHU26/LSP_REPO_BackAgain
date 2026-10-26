package org.howard.edu.lsp.midterm.inheritance;

/**
 * Abstract base class for all employee types. It holds the data every
 * employee has (hours worked and hourly rate) and declares
 * {@link #calculatePay()}, which each subclass overrides with its own pay
 * rule. Callers use an {@code Employee} reference and never need to check
 * the employee type.
 *
 * @author Noah
 */
public abstract class Employee {

    private final double hoursWorked;
    private final double hourlyRate;

    /**
     * Creates an employee.
     *
     * @param hoursWorked the hours worked in the week (nonnegative)
     * @param hourlyRate  the normal hourly rate (nonnegative)
     */
    protected Employee(double hoursWorked, double hourlyRate) {
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    /**
     * Returns the hours worked.
     *
     * @return the hours worked
     */
    public double getHoursWorked() {
        return hoursWorked;
    }

    /**
     * Returns the normal hourly rate.
     *
     * @return the hourly rate
     */
    public double getHourlyRate() {
        return hourlyRate;
    }

    /**
     * Calculates this employee's pay according to the rule for the
     * employee's type.
     *
     * @return the calculated pay
     */
    public abstract double calculatePay();
}
