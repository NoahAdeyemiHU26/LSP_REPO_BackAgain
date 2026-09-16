//Generated via Claude AI (https://claude.ai/share/e60f6cde-fd41-4587-a34b-ccd46312b10f)
//Noah Adeyemi
package org.howard.edu.lsp.assignment3;

/**
 * RunSummary
 *
 * Holds the four figures the assignment requires at the end of a run
 * (rows read, rows transformed, rows skipped, output path) and knows how
 * to print itself to the console. Pulling this out of ETLPipeline keeps
 * "what the summary contains and how it's displayed" separate from
 * "how the pipeline stages are run."
 */
public class RunSummary {

    private final int rowsRead;
    private final int rowsTransformed;
    private final int rowsSkipped;
    private final String outputPath;

    public RunSummary(int rowsRead, int rowsTransformed, int rowsSkipped, String outputPath) {
        this.rowsRead = rowsRead;
        this.rowsTransformed = rowsTransformed;
        this.rowsSkipped = rowsSkipped;
        this.outputPath = outputPath;
    }

    /**
     * Prints the run summary to the console (never to the output CSV).
     */
    public void printToConsole() {
        System.out.println("Rows read: " + rowsRead);
        System.out.println("Rows transformed: " + rowsTransformed);
        System.out.println("Rows skipped: " + rowsSkipped);
        System.out.println("Output file: " + outputPath);
    }
}
