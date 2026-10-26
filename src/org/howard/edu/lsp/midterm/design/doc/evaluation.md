# Evaluation of the `CourseRegistration` Design

## Summary
`CourseRegistration` is a single class whose one method, `registerStudent()`, does everything: it calculates tuition, applies a discount, prints a confirmation, writes to a file, "sends" an email, and logs. The class has many reasons to change and breaks several of Arthur Riel's design heuristics.

## Issues

### 1. Public data (Riel 2.1: all data should be hidden within its class)
`studentId`, `studentName`, `email`, `courseCode`, and `creditHours` are `public` fields. Any class can set `creditHours` to a negative number or leave `courseCode` null, so the class cannot protect its own invariants. Changing a field's type or name breaks every user of the class.

### 2. A class should capture one and only one key abstraction (Riel 2.8)
The class mixes student data (id, name, email), course data (code, credit hours), and the registration event itself. It is not clear whether an object represents a student, a course, or a registration. A registration with a single `courseCode` and `creditHours` also cannot represent a student taking several courses, yet the "heavy course load" discount (12+ hours) only makes sense across several courses, which is a correctness problem.

### 3. God method / god class behavior (Riel 3.2; Single Responsibility Principle)
`registerStudent()` performs at least six unrelated jobs: tuition calculation, discount policy, console output, file persistence, notification, and logging. A change to any one (new tuition rate, database storage, text-message notification) forces edits to this same method, which makes it fragile and hard to test.

### 4. Related data and behavior are not kept together (Riel 2.9)
Tuition depends on `creditHours`, but the pricing rule lives in a method that also does I/O, and the rate (750.0) and discount rule (>= 12 hours, 10%) are magic numbers buried inline. Pricing policy has no home of its own, so it cannot be reused or changed without touching registration code.

### 5. Poor extensibility (Open/Closed and Dependency Inversion principles)
The class depends directly on concrete details: `FileWriter`, a hard-coded file name `"registrations.txt"`, `System.out`, and `new Date()`. Supporting a database or text messages means editing this class instead of adding new code. High-level registration logic depends on low-level details rather than abstractions.

### 6. Correctness and robustness problems
- The `FileWriter` is not closed if `write` throws, so a resource can leak (try-with-resources should be used).
- `catch (Exception e)` with `e.printStackTrace()` swallows the failure: registration continues and reports success even though nothing was saved.
- Tuition is a `double`, which is unsuitable for money (rounding error); `BigDecimal` or integer cents is safer.
- No validation: negative credit hours, null email, or an empty student id are accepted.
- The "email" is only a `println`, so the method claims to send a confirmation that never happens.
- The comma-separated file line is not escaped, so a value containing a comma corrupts the record.

### 7. Hard to test
Because output goes to `System.out`, the file system, and the current clock, a unit test cannot check tuition or the saved record without side effects. Nothing can be substituted with a test double.

## Conclusion
The design concentrates unrelated responsibilities and exposes its data. A better design separates the key abstractions (student, course, registration), moves pricing into its own class, and hides storage and notification behind interfaces. The redesign is in `design.md`.
