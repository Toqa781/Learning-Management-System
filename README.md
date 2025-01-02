# Learning-Management-System

## Project Overview
The Java-Based Learning Management System (LMS) is a backend-focused web application for managing and organizing online courses and assessments. It is designed to cater to the needs of administrators, instructors, and students, providing functionalities such as course management, user management, assessments, and performance tracking.

**Note:** This project does not include a front-end interface. All functionalities were tested using Postman.

## Key Features

### 1. User Management
- **User Roles:** Admin, Instructor, Student
  - **Admin:** Manages system settings, creates users, and oversees courses.
  - **Instructor:** Creates courses, manages content, assigns and grades assessments, and handles student enrollments.
  - **Student:** Enrolls in courses, accesses materials, submits assignments, and views grades.
- **Core Features:**
  - Role-based registration and login.
  - Profile management for viewing and updating user details.

### 2. Course Management
- **Course Creation:**
  - Instructors can create courses with details like title, description, and duration.
  - Support for media uploads (videos, PDFs, audio, etc.).
- **Enrollment Management:**
  - Students can view and enroll in available courses.
  - Admins and instructors can view the list of enrolled students.
- **Attendance Management:**
  - Instructors can generate OTPs for lessons to track attendance.
  - Students mark attendance by entering the OTP.

### 3. Assessments & Grading
- **Assessment Types:** Quizzes and Assignments
- **Quiz Features:**
  - Creation of quizzes with multiple question types (MCQs, true/false, short answers).
  - Questions bank for each course and randomized question selection for quizzes.
- **Assignment Features:**
  - Students upload files for assignment submissions.
- **Grading and Feedback:**
  - Automated feedback for quizzes.
  - Manual grading and feedback for assignments.

### 4. Performance Tracking
- Instructors can track:
  - Quiz scores.
  - Assignment submissions.
  - Attendance records.

### 5. Notifications
- **System Notifications:**
  - Students receive notifications for enrollment confirmations, graded assignments, and updates.
  - Instructors get notifications for new student enrollments.
  - 
### Bonus Features
- **Role-Based Access Control:**
  - Spring Security for authentication and authorization.
  - Access permissions based on user roles.
- **Performance Analytics:**
  - Admins and instructors can generate Excel reports on student performance (grades and attendance).

## Tools & Technologies
- **Database:** H2 Database
- **Backend:** Java (Spring Boot Framework)
- **Testing:** Postman for API testing, JUnit for unit testing

## How to Run
1. Clone the repository to your local machine.
2. Set up the H2 Database.
3. Configure the application properties for the database connection.
4. Use Postman to test the API endpoints.
