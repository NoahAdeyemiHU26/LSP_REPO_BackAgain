# Design Discussion: Assignment 2 vs. Assignment 3

## How was the Assignment #2 solution organized?

Assignment #2 was implemented as a single class, `ETLPipeline`, with one
large `main()` method. That method did everything: opened the input
file, looped over each line, split and trimmed fields inline, validated
and parsed numbers inline, calculated overtime pay and the IT bonus
inline, rounded the result, determined `PayLevel` and
`EmploymentStatus` with inline `if/else` chains, built the output string
for each row, wrote the output file, and printed the run summary — all
in one procedural block. A single private helper method,
`determinePayLevel()`, was the only logic pulled out of `main()`. There
was no data model; an employee's fields existed only as local variables
for the duration of one loop iteration.

## What design changes were made for Assignment #3?

The single procedural method was broken into eight small, single-purpose
classes, each responsible for one part of the pipeline:

- **`Employee`** — a domain model holding one employee's validated input
  fields (`employeeId`, `name`, `department`, `hoursWorked`,
  `hourlyRate`) plus its derived results (`grossPay`, `payLevel`,
  `employmentStatus`) once they're calculated. It also formats itself as
  an output CSV row via `toCsvRow()`.
- **`InvalidRowException`** — a custom checked exception used to signal
  *why* a row failed validation, replacing the skip-and-`continue` logic
  that was previously inline.
- **`EmployeeRowParser`** — turns one raw CSV line into a validated
  `Employee`, or throws `InvalidRowException`. This is where field
  normalization and validation now live.
- **`EmployeeCsvReader`** — the Extract stage. Reads the input file line
  by line, delegates parsing to `EmployeeRowParser`, and tracks the
  rows-read/rows-skipped counts.
- **`PayrollCalculator`** — a stateless utility class holding all of the
  payroll business rules (overtime, IT bonus, rounding, `PayLevel`
  bands, `EmploymentStatus` threshold) in one place.
- **`EmployeeCsvWriter`** — the Load stage. Writes the header and each
  employee's formatted row to the output file.
- **`RunSummary`** — a small data holder for the four end-of-run figures,
  with a method to print them.
- **`ETLPipeline`** — now just an orchestrator. Its `main()` method calls
  the reader, then the calculator, then the writer, then prints the
  summary — nothing else.

## What classes or abstractions were introduced, and why?

The biggest change is introducing `Employee` as an actual object instead
of a set of loose local variables. Once employee data has a name and an
identity, the rest of the design follows naturally: a parser that builds
`Employee` objects, a calculator that operates on them, and a writer
that formats them.

`PayrollCalculator` was split out specifically because the payroll rules
(overtime math, the IT bonus, rounding, the `PayLevel`/`EmploymentStatus`
thresholds) are the part of the assignment most likely to be
re-examined, adjusted, or reused independently of *how* the data is read
or written. Keeping that logic in one class, with no dependency on file
I/O, means it can be understood as a self-contained set of business
rules.

`InvalidRowException` replaces implicit skip logic with an explicit,
named signal. Instead of a validation step silently deciding to
`continue`, `EmployeeRowParser` now states clearly, in one place, all
the reasons a row can be rejected.

## How were responsibilities divided differently?

In Assignment #2, one class (and effectively one method) had every
responsibility. In Assignment #3, each class has exactly one reason to
change:

- If the input file format changes, only `EmployeeRowParser` changes.
- If the payroll rules change, only `PayrollCalculator` changes.
- If the output format changes, only `Employee.toCsvRow()` and/or
  `EmployeeCsvWriter` change.
- If the run summary's wording changes, only `RunSummary` changes.
- `ETLPipeline` itself should rarely need to change at all, since it
  contains no business logic of its own.

## Why is the Assignment #3 design an improvement?

The Assignment #2 solution worked, but every rule of the spec was
tangled together in one method, which made it hard to see where one
step ended and the next began, and risky to change one rule without
affecting another. The Assignment #3 design maps directly onto the
Extract-Transform-Load structure described in the assignment itself:
`EmployeeCsvReader` is Extract, `PayrollCalculator` (applied to each
`Employee`) is Transform, and `EmployeeCsvWriter` is Load. Each class is
small enough to read and reason about on its own, and the behavior
verified against the Assignment #2 grading dataset — matching output
file and console summary — is unchanged.

## AI and Internet Resource Disclosure

This assignment was completed with the assistance of Claude (Anthropic).
Claude was used to help design the class breakdown (identifying
`Employee`, `PayrollCalculator`, `EmployeeRowParser`,
`EmployeeCsvReader`, `EmployeeCsvWriter`, `RunSummary`, and
`InvalidRowException` as separate responsibilities), to write the
resulting Java source files and their comments, and to verify that the
refactored program's output matches the Assignment #2 output exactly on
the provided grading dataset. No other Internet resources were used.

[Link to AI transcript: ADD YOUR CONVERSATION LINK HERE]
