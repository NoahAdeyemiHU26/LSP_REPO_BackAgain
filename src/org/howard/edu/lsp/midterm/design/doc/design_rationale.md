# Evolving Requirements and Design Rationale

## How the design accommodates the changes
`RegistrationService` depends only on the `RegistrationRepository` and `ConfirmationNotifier` interfaces from Part 2, so supporting two storage options and two confirmation channels means adding new classes rather than editing existing ones:

- `FileRegistrationRepository` and `DatabaseRegistrationRepository` both implement `RegistrationRepository`.
- `EmailConfirmationNotifier` and `TextMessageConfirmationNotifier` both implement `ConfirmationNotifier`.

`RegistrationService`, `TuitionCalculator`, `Registration`, `Student`, and `Course` are unchanged. This follows the Open/Closed Principle and Dependency Inversion: the high-level workflow depends on abstractions, and new technologies are plugged in underneath.

## Changes to the CRC cards
1. **Add four concrete classes**, each collaborating with `Registration` (and `Student` for the notifiers), as described above.
2. **Add an `ApplicationConfiguration` class** (new card below) that chooses which implementations to use for the deployment environment.
3. **Student:** gains a phone number (already listed in Part 2) so the text-message notifier has something to send to; no other card changes.

Class: ApplicationConfiguration
Responsibilities:
- Read the deployment setting (file or database storage; email or text confirmation)
- Create the chosen `RegistrationRepository` and `ConfirmationNotifier`
- Assemble a `RegistrationService` with those choices
Collaborators:
- RegistrationService
- RegistrationRepository
- ConfirmationNotifier

## Key decision: who owns the choice of storage and channel?
I assign the responsibility of *choosing* the concrete repository and notifier to `ApplicationConfiguration`, not to `RegistrationService`.

If `RegistrationService` made the choice (for example, `if (env.equals("db")) ...`), it would again contain deployment details and conditionals and need editing every time a new option is added, which is the same flaw the original `CourseRegistration` had. Selecting implementations is a deployment concern, so it belongs at the edge of the system where the environment is known. This keeps `RegistrationService` limited to one responsibility, coordinating the workflow, and lets tests supply simple fake repositories and notifiers without any configuration. It is consistent with the Part 2 cards, where `RegistrationService` already collaborates only with interfaces.
