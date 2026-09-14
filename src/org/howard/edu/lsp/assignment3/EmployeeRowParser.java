package org.howard.edu.lsp.assignment3;

/**
 * EmployeeRowParser
 *
 * Responsible for turning one raw CSV data line into a validated Employee
 * object. This is where "Transform Step 1" (normalize fields) and
 * "Transform Step 2" (validate numeric values) from the assignment
 * specification live, plus the row-shape checks (blank line, wrong field
 * count) that must happen before normalization can occur.
 *
 * Keeping this logic in its own class (rather than inline in the file
 * reading loop) means the row-level validation rules can be understood,
 * and reused, independently of how the file itself is read.
 */
public class EmployeeRowParser {

    private static final int EXPECTED_FIELD_COUNT = 5;

    /**
     * Parses and validates a single raw (un-trimmed) CSV data line.
     *
     * @param rawLine one line from the input file, not including the header
     * @return a new Employee built from the normalized, validated fields
     * @throws InvalidRowException if the row is blank, has the wrong
     *         number of fields, has an EmployeeID that isn't an integer,
     *         has HoursWorked/HourlyRate that aren't valid decimals, or
     *         has a negative HoursWorked or HourlyRate
     */
    public Employee parse(String rawLine) throws InvalidRowException {

        if (rawLine.trim().isEmpty()) {
            throw new InvalidRowException("Blank row");
        }

        // Limit -1 preserves trailing empty fields, so a row missing its
        // last column is correctly detected as having too few fields.
        String[] rawFields = rawLine.split(",", -1);
        if (rawFields.length != EXPECTED_FIELD_COUNT) {
            throw new InvalidRowException(
                    "Expected " + EXPECTED_FIELD_COUNT + " fields but found " + rawFields.length);
        }

        // Normalize: trim every field; uppercase the Name; Department is
        // trimmed only.
        String rawEmployeeId = rawFields[0].trim();
        String name = rawFields[1].trim().toUpperCase();
        String department = rawFields[2].trim();
        String rawHoursWorked = rawFields[3].trim();
        String rawHourlyRate = rawFields[4].trim();

        int employeeId;
        try {
            employeeId = Integer.parseInt(rawEmployeeId);
        } catch (NumberFormatException e) {
            throw new InvalidRowException("Invalid EmployeeID: '" + rawEmployeeId + "'");
        }

        double hoursWorked;
        double hourlyRate;
        try {
            hoursWorked = Double.parseDouble(rawHoursWorked);
            hourlyRate = Double.parseDouble(rawHourlyRate);
        } catch (NumberFormatException e) {
            throw new InvalidRowException("HoursWorked/HourlyRate are not valid numbers");
        }

        if (hoursWorked < 0 || hourlyRate < 0) {
            throw new InvalidRowException("HoursWorked and HourlyRate may not be negative");
        }

        return new Employee(employeeId, name, department, hoursWorked, hourlyRate);
    }
}
