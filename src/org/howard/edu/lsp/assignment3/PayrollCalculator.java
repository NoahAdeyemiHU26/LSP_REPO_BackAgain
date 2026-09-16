//Generated via Claude AI (https://claude.ai/share/e60f6cde-fd41-4587-a34b-ccd46312b10f)
//Noah Adeyemi

package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * PayrollCalculator
 *
 * A stateless utility class that implements Transform Steps 3-7 from the
 * assignment specification: base/overtime pay, the IT department bonus,
 * rounding, PayLevel, and EmploymentStatus.
 *
 * Isolating the business-rule math here (separate from Employee, which is
 * just a data holder, and separate from EmployeeCsvReader/Writer, which
 * handle file I/O) means the payroll rules can be read, changed, or
 * tested as a single, self-contained unit.
 *
 * This class has no instance state and is never instantiated - all
 * behavior is exposed through the static calculateAndApply() method.
 */
public final class PayrollCalculator {

    private static final double OVERTIME_THRESHOLD_HOURS = 40.00;
    private static final double OVERTIME_MULTIPLIER = 1.5;
    private static final String IT_DEPARTMENT_NAME = "IT";
    private static final BigDecimal IT_BONUS_RATE = new BigDecimal("0.05");
    private static final double FULL_TIME_THRESHOLD_HOURS = 30.00;

    private static final BigDecimal LOW_STANDARD_BOUNDARY = new BigDecimal("500.00");
    private static final BigDecimal STANDARD_HIGH_BOUNDARY = new BigDecimal("1000.00");
    private static final BigDecimal HIGH_EXECUTIVE_BOUNDARY = new BigDecimal("2000.00");

    // Utility class: no instances.
    private PayrollCalculator() {
    }

    /**
     * Computes GrossPay, PayLevel, and EmploymentStatus for the given
     * employee (using its already-validated HoursWorked, HourlyRate, and
     * Department) and stores the results back onto that employee.
     */
    public static void calculateAndApply(Employee employee) {
        double hoursWorked = employee.getHoursWorked();
        double hourlyRate = employee.getHourlyRate();

        BigDecimal grossPay = calculateBasePayWithOvertime(hoursWorked, hourlyRate);
        grossPay = applyItBonusIfApplicable(grossPay, employee.getDepartment());
        grossPay = grossPay.setScale(2, RoundingMode.HALF_UP);

        String payLevel = determinePayLevel(grossPay);
        String employmentStatus = determineEmploymentStatus(hoursWorked);

        employee.setPayrollResults(grossPay, payLevel, employmentStatus);
    }

    /**
     * Step 3: hours up to and including 40 are paid at the normal rate;
     * hours beyond 40 are paid at 1.5x the normal rate. Uses the raw
     * (unrounded) HourlyRate, per the assignment's numeric rule.
     */
    private static BigDecimal calculateBasePayWithOvertime(double hoursWorked, double hourlyRate) {
        double grossPayRaw;
        if (hoursWorked <= OVERTIME_THRESHOLD_HOURS) {
            grossPayRaw = hoursWorked * hourlyRate;
        } else {
            double regularHours = OVERTIME_THRESHOLD_HOURS;
            double overtimeHours = hoursWorked - OVERTIME_THRESHOLD_HOURS;
            grossPayRaw = (regularHours * hourlyRate)
                    + (overtimeHours * hourlyRate * OVERTIME_MULTIPLIER);
        }
        return BigDecimal.valueOf(grossPayRaw);
    }

    /**
     * Step 4: a 5% bonus applies only when Department is exactly "IT"
     * (case-sensitive, after trimming), applied after overtime.
     */
    private static BigDecimal applyItBonusIfApplicable(BigDecimal grossPay, String department) {
        if (IT_DEPARTMENT_NAME.equals(department)) {
            return grossPay.multiply(BigDecimal.ONE.add(IT_BONUS_RATE));
        }
        return grossPay;
    }

    /**
     * Step 6: PayLevel is determined from the final, rounded GrossPay.
     */
    private static String determinePayLevel(BigDecimal grossPay) {
        if (grossPay.compareTo(LOW_STANDARD_BOUNDARY) < 0) {
            return "Low";
        } else if (grossPay.compareTo(STANDARD_HIGH_BOUNDARY) < 0) {
            return "Standard";
        } else if (grossPay.compareTo(HIGH_EXECUTIVE_BOUNDARY) < 0) {
            return "High";
        } else {
            return "Executive";
        }
    }

    /**
     * Step 7: EmploymentStatus is determined from HoursWorked.
     */
    private static String determineEmploymentStatus(double hoursWorked) {
        return (hoursWorked < FULL_TIME_THRESHOLD_HOURS) ? "Part-Time" : "Full-Time";
    }
}
