package org.howard.edu.lsp.assignment3;

/**
 * Signals that a single CSV row failed validation during the Extract
 * stage (blank line, wrong number of fields, unparsable numeric values,
 * or negative HoursWorked/HourlyRate).
 *
 * Using an exception here (rather than returning null or a boolean flag)
 * lets EmployeeRowParser communicate exactly *why* a row was rejected,
 * while keeping the "row is invalid" control flow separate from the
 * normal "row parsed successfully" path.
 */
public class InvalidRowException extends Exception {

    public InvalidRowException(String message) {
        super(message);
    }
}
