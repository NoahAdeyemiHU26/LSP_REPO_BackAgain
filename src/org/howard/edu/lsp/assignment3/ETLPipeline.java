package org.howard.edu.lsp.assignment3;

import java.io.IOException;
import java.util.List;

/**
 * ETLPipeline
 *
 * Entry point and orchestrator for the employee payroll ETL pipeline.
 * All of the actual work is delegated to focused collaborator classes:
 *
 *   EmployeeCsvReader   - Extract:   reads and validates data/employees.csv
 *   PayrollCalculator   - Transform: computes GrossPay, PayLevel, EmploymentStatus
 *   EmployeeCsvWriter   - Load:      writes data/transformed_employees.csv
 *   RunSummary          - Reports the final row counts and output path
 *
 * ETLPipeline itself contains no business rules or file-format details;
 * its only job is to run the three ETL stages in order and report the
 * result. This mirrors the Extract -> Transform -> Load structure of the
 * assignment specification directly in the class design.
 */
public class ETLPipeline {

    private static final String INPUT_PATH = "data/employees.csv";
    private static final String OUTPUT_PATH = "data/transformed_employees.csv";

    public static void main(String[] args) {

        EmployeeCsvReader reader = new EmployeeCsvReader();
        List<Employee> employees;

        // ---------------------------------------------------------------
        // EXTRACT
        // ---------------------------------------------------------------
        try {
            employees = reader.readEmployees(INPUT_PATH);
        } catch (IOException e) {
            System.out.println("Error reading input file '" + INPUT_PATH + "': " + e.getMessage());
            return;
        }

        // ---------------------------------------------------------------
        // TRANSFORM
        // ---------------------------------------------------------------
        for (Employee employee : employees) {
            PayrollCalculator.calculateAndApply(employee);
        }

        // ---------------------------------------------------------------
        // LOAD
        // ---------------------------------------------------------------
        EmployeeCsvWriter writer = new EmployeeCsvWriter();
        try {
            writer.writeEmployees(OUTPUT_PATH, employees);
        } catch (IOException e) {
            System.out.println("Error writing output file '" + OUTPUT_PATH + "': " + e.getMessage());
            return;
        }

        // ---------------------------------------------------------------
        // REPORT
        // ---------------------------------------------------------------
        RunSummary summary = new RunSummary(
                reader.getRowsRead(), employees.size(), reader.getRowsSkipped(), OUTPUT_PATH);
        summary.printToConsole();
    }
}
