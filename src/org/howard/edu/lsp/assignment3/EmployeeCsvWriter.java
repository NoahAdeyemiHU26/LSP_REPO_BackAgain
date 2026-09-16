//Generated via Claude AI (https://claude.ai/share/e60f6cde-fd41-4587-a34b-ccd46312b10f)
//Noah Adeyemi
package org.howard.edu.lsp.assignment3;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * EmployeeCsvWriter
 *
 * Handles the Load stage: writes a header row followed by one formatted
 * row per transformed Employee to the output CSV file. Each Employee
 * knows how to format itself (Employee.toCsvRow()), so this class is
 * only responsible for the file-level concerns: opening the file,
 * writing the header, writing each row, and closing the file.
 */
public class EmployeeCsvWriter {

    private static final String HEADER =
            "EmployeeID,Name,Department,HoursWorked,HourlyRate,GrossPay,PayLevel,EmploymentStatus";

    /**
     * Writes the header and all transformed employee rows to the given
     * output file, overwriting it if it already exists.
     *
     * @param outputPath relative path to the output CSV file
     * @param employees  the transformed employees to write, already
     *                   carrying their payroll results
     * @throws IOException if the file cannot be created or written to
     */
    public void writeEmployees(String outputPath, List<Employee> employees) throws IOException {
        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write(HEADER + System.lineSeparator());
            for (Employee employee : employees) {
                writer.write(employee.toCsvRow() + System.lineSeparator());
            }
        }
    }
}
