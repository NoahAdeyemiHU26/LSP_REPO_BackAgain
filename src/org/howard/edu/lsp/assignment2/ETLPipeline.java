/**
*Noah Adeyemi (Generated via Claude AI)
**/
package org.howard.edu.lsp.assignment2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * ETLPipeline
 *
 * A simple Extract-Transform-Load (ETL) pipeline for employee payroll data.
 *
 * Extract:   Reads raw employee records from data/employees.csv
 * Transform: Normalizes fields, validates numeric values, calculates gross
 *            pay (including overtime and an IT department bonus), and
 *            derives a PayLevel and EmploymentStatus for each valid record.
 * Load:      Writes the transformed records to data/transformed_employees.csv
 *
 * This class contains only plain Java (no third-party libraries) and is
 * intended to be run directly via its main() method, with no command-line
 * arguments or keyboard input required.
 */

public class ETLPipeline {
 // Required relative file paths (from the project root).
    private static final String INPUT_PATH = "data/employees.csv";
    private static final String OUTPUT_PATH = "data/transformed_employees.csv";
 
// Business rules used during the Transform stage.
    private static final double OVERTIME_THRESHOLD_HOURS = 40.00;
    private static final double OVERTIME_MULTIPLIER = 1.5;
    private static final String IT_DEPARTMENT_NAME = "IT";
    private static final BigDecimal IT_BONUS_RATE = new BigDecimal("0.05");
    private static final double FULL_TIME_THRESHOLD_HOURS = 30.00;
 
/**
 * Entry point. Runs the complete Extract -> Transform -> Load pipeline
 * using the fixed input/output paths required by the assignment.
 */
public static void main(String[] args) {
 
    // Counters used to build the run summary printed at the end.
    int rowsRead = 0;
    int rowsTransformed = 0;
    int rowsSkipped = 0;
 
    // Holds the fully-formatted output lines (header + transformed rows)
    // that will be written to the output CSV during the Load stage.
    StringBuilder outputBuilder = new StringBuilder();
    outputBuilder.append("EmployeeID,Name,Department,HoursWorked,HourlyRate,GrossPay,PayLevel,EmploymentStatus")
        .append(System.lineSeparator());
 
    // ---------------------------------------------------------------
    // EXTRACT: Read every line of the input CSV file.
    // ---------------------------------------------------------------
    try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_PATH))) {
 
        String line = reader.readLine(); // Read and discard the header row.
        boolean isFirstDataLine = true;
 
        while ((line = reader.readLine()) != null) {
 
            // Every non-header line encountered counts as "read",
            // including blank lines and malformed lines.
            rowsRead++;
 
            // -------------------------------------------------------
            // TRANSFORM STEP 1 (part A): Split the raw line into fields
            // and validate that the row has the expected shape before
            // attempting any further processing.
            // -------------------------------------------------------
            if (line.trim().isEmpty()) {
                // Rule: skip blank rows.
                rowsSkipped++;
                continue;
            }
 
            // Split on commas. Using -1 as the limit preserves trailing
            // empty fields so a row like "105,Name,Finance,40" (missing
            // HourlyRate) is correctly detected as having too few fields.
            String[] rawFields = line.split(",", -1);
 
            if (rawFields.length != 5) {
                // Rule: skip rows that do not contain exactly 5 fields.
                rowsSkipped++;
                continue;
            }
 
            // -------------------------------------------------------
            // TRANSFORM STEP 1 (part B): Normalize fields.
            // Trim whitespace from every field, then uppercase the Name.
            // Department is trimmed but otherwise left unchanged.
            // -------------------------------------------------------
            String rawEmployeeId = rawFields[0].trim();
            String name = rawFields[1].trim().toUpperCase();
            String department = rawFields[2].trim();
            String rawHoursWorked = rawFields[3].trim();
            String rawHourlyRate = rawFields[4].trim();
 
            // -------------------------------------------------------
            // TRANSFORM STEP 2: Validate numeric values.
            // EmployeeID must parse as an integer; HoursWorked and
            // HourlyRate must parse as decimals and be non-negative.
            // Any failure here causes the row to be skipped.
            // -------------------------------------------------------
            int employeeId;
            try {
                employeeId = Integer.parseInt(rawEmployeeId);
            } 
            catch (NumberFormatException e) {
                rowsSkipped++;
                continue;
            }
 
            double hoursWorked;
            double hourlyRate;
            try {
                hoursWorked = Double.parseDouble(rawHoursWorked);
                hourlyRate = Double.parseDouble(rawHourlyRate);
            } 
            catch (NumberFormatException e) {
                rowsSkipped++;
                continue;
            }
 
            if (hoursWorked < 0 || hourlyRate < 0) {
                // Rule: negative HoursWorked or HourlyRate is invalid.
                rowsSkipped++;
                continue;
            }
 
            // -------------------------------------------------------
            // TRANSFORM STEP 3: Calculate base/overtime pay.
            // Hours up to and including 40 are paid at the normal rate.
            // Hours beyond 40 are paid at 1.5x the normal rate.
            // Note: HourlyRate is used at full precision here; it is
            // only formatted to two decimal places for display later.
            // -------------------------------------------------------
            double grossPayRaw;
            if (hoursWorked <= OVERTIME_THRESHOLD_HOURS) {
                grossPayRaw = hoursWorked * hourlyRate;
            } 
            else {
                double regularHours = OVERTIME_THRESHOLD_HOURS;
                double overtimeHours = hoursWorked - OVERTIME_THRESHOLD_HOURS;
                grossPayRaw = (regularHours * hourlyRate) + (overtimeHours * hourlyRate * OVERTIME_MULTIPLIER);
            }
 
            // -------------------------------------------------------
            // TRANSFORM STEP 4: Apply the IT department bonus (5%),
            // applied after overtime, only when Department is exactly
            // "IT" (case-sensitive, after trimming).
            // -------------------------------------------------------
            BigDecimal grossPay = BigDecimal.valueOf(grossPayRaw);
            if (IT_DEPARTMENT_NAME.equals(department)) {
                BigDecimal bonusMultiplier = BigDecimal.ONE.add(IT_BONUS_RATE);
                grossPay = grossPay.multiply(bonusMultiplier);
            }
 
            // -------------------------------------------------------
            // TRANSFORM STEP 5: Round GrossPay to exactly two decimal
            // places using round-half-up.
            // -------------------------------------------------------
            grossPay = grossPay.setScale(2, RoundingMode.HALF_UP);
 
            // -------------------------------------------------------
            // TRANSFORM STEP 6: Determine PayLevel from the final
            // rounded GrossPay.
            // -------------------------------------------------------
            String payLevel = determinePayLevel(grossPay);
 
            // -------------------------------------------------------
            // TRANSFORM STEP 7: Determine EmploymentStatus from
            // HoursWorked.
            // -------------------------------------------------------
            String employmentStatus = (hoursWorked < FULL_TIME_THRESHOLD_HOURS)
                ? "Part-Time"
                : "Full-Time";
 
            // -------------------------------------------------------
            // Build the formatted output row. HoursWorked, HourlyRate,
            // and GrossPay are always written with exactly two decimal
            // places (formatting only; does not affect calculations).
            // -------------------------------------------------------
            String formattedHoursWorked = String.format("%.2f", hoursWorked);
            String formattedHourlyRate = String.format("%.2f", hourlyRate);
            String formattedGrossPay = grossPay.toPlainString();
 
            String outputRow = String.join(",",
            String.valueOf(employeeId), name, department, formattedHoursWorked, formattedHourlyRate, formattedGrossPay, payLevel, employmentStatus);
 
            outputBuilder.append(outputRow).append(System.lineSeparator());
            rowsTransformed++;
        }
 
    } 
    catch (IOException e) {
        // If the input file cannot be read at all, report the problem
        // clearly and stop, rather than letting the program crash with
        // an unhandled exception.
        System.out.println("Error reading input file '" + INPUT_PATH + "': " + e.getMessage());
        return;
    }
 
    // ---------------------------------------------------------------
    // LOAD: Write all transformed rows to the output CSV file.
    // ---------------------------------------------------------------
    try (FileWriter writer = new FileWriter(OUTPUT_PATH)) {
        writer.write(outputBuilder.toString());
    } 
    catch (IOException e) {
        System.out.println("Error writing output file '" + OUTPUT_PATH + "': " + e.getMessage());
        return;
    }
 
    // ---------------------------------------------------------------
    // Print the run summary to the console (never to the CSV file).
    // ---------------------------------------------------------------
    System.out.println("Rows read: " + rowsRead);
    System.out.println("Rows transformed: " + rowsTransformed);
    System.out.println("Rows skipped: " + rowsSkipped);
    System.out.println("Output file: " + OUTPUT_PATH);
    }
 
    /**
     * Determines the PayLevel category for a given (already rounded)
     * GrossPay amount, per the assignment specification:
     *   less than $500.00        -> Low
     *   $500.00 to $999.99       -> Standard
     *   $1000.00 to $1999.99     -> High
     *   $2000.00 or more         -> Executive
     *
     * @param grossPay the final, rounded gross pay amount
     * @return the PayLevel label as a String
     */
    private static String determinePayLevel(BigDecimal grossPay) {
        if (grossPay.compareTo(new BigDecimal("500.00")) < 0) {
            return "Low";
        } 
        else if (grossPay.compareTo(new BigDecimal("1000.00")) < 0) {
            return "Standard";
        } 
        else if (grossPay.compareTo(new BigDecimal("2000.00")) < 0) {
            return "High";
        } 
        else {
            return "Executive";
        }
    }
}
