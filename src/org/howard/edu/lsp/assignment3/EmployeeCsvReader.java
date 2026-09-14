package org.howard.edu.lsp.assignment3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * EmployeeCsvReader
 *
 * Handles the Extract stage: reads a CSV file line by line, skips the
 * header, and uses an EmployeeRowParser to turn each remaining line into
 * an Employee. Rows that fail validation are counted as skipped rather
 * than causing the program to stop.
 *
 * This class owns the row-count bookkeeping (rows read, rows skipped)
 * since it is the only place that sees every line of the input file,
 * including the ones that never become Employee objects.
 */
public class EmployeeCsvReader {

    private final EmployeeRowParser rowParser = new EmployeeRowParser();

    private int rowsRead;
    private int rowsSkipped;

    /**
     * Reads and parses every valid employee record from the given file.
     *
     * @param inputPath relative path to the input CSV file
     * @return a list of successfully validated Employee objects, in the
     *         order they appeared in the file (excluding the header)
     * @throws IOException if the file cannot be opened or read
     */
    public List<Employee> readEmployees(String inputPath) throws IOException {
        List<Employee> employees = new ArrayList<>();
        rowsRead = 0;
        rowsSkipped = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputPath))) {
            reader.readLine(); // Discard the header row; it is never transformed.

            String line;
            while ((line = reader.readLine()) != null) {
                // Every non-header line counts as "read", including blank
                // and malformed lines.
                rowsRead++;

                try {
                    employees.add(rowParser.parse(line));
                } catch (InvalidRowException e) {
                    rowsSkipped++;
                }
            }
        }

        return employees;
    }

    public int getRowsRead() {
        return rowsRead;
    }

    public int getRowsSkipped() {
        return rowsSkipped;
    }
}
