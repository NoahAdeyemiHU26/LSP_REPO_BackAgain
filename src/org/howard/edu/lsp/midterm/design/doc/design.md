# Improved Design – CRC Cards

Class: Student
Responsibilities:
- Store student identity (student id, name, email, phone number)
- Validate its own data (for example, id and email present)
- Provide contact details to those who need them
Collaborators:
- None

Class: Course
Responsibilities:
- Store course code and credit hours
- Validate credit hours (positive)
- Provide course details
Collaborators:
- None

Class: Registration
Responsibilities:
- Represent one student's registration for one or more courses
- Hold the registration date and the computed tuition amount
- Report total credit hours across its courses
Collaborators:
- Student
- Course

Class: TuitionCalculator
Responsibilities:
- Own the tuition pricing rules (rate per credit hour)
- Own the heavy-course-load discount rule (12 or more credit hours)
- Calculate tuition for a registration using exact decimal arithmetic
Collaborators:
- Registration

Class: RegistrationService
Responsibilities:
- Coordinate the registration workflow: validate, calculate tuition, save, notify, log
- Report failures instead of hiding them (for example, do not notify if saving failed)
- Contain no storage, messaging, or pricing details of its own
Collaborators:
- Registration
- TuitionCalculator
- RegistrationRepository
- ConfirmationNotifier
- RegistrationLogger

Class: RegistrationRepository (interface)
Responsibilities:
- Define how a registration is saved and retrieved, independent of storage technology
Collaborators:
- Registration

Class: ConfirmationNotifier (interface)
Responsibilities:
- Define how a confirmation is delivered to a student, independent of the channel
Collaborators:
- Registration
- Student

Class: RegistrationLogger
Responsibilities:
- Record that a registration was processed, with a timestamp
Collaborators:
- Registration
