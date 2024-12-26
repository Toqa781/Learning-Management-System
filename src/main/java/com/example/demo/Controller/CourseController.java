package com.example.demo.Controller;

import com.example.demo.Model.Course;
import com.example.demo.Model.Lesson;
import com.example.demo.Model.Users.Instructor;
import com.example.demo.Model.Users.Student;
import com.example.demo.Service.*;
import com.example.demo.Service.Authentication.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin
//@RequestMapping("/{userId}/courses")
@RequestMapping("/courses")

public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private JWTService jwtService;

    // Constructor-based Dependency Injection
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/create") //course
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public ResponseEntity<?> createCourse(@RequestBody Course course) {
        Course course2 = courseService.getCourseById(course.getCourseId());
        if (course2 != null) {
            return ResponseEntity.status(HttpStatus.FOUND).body("Course with the same ID already exists.");
        }

        String instructorId = SecurityContextHolder.getContext().getAuthentication().getName();
        Instructor instructor = new Instructor();
        instructor.setUserId(instructorId);
        course.setCreator(instructor);
        courseService.createCourse(course.getCourseId(), course.getCourseName(), course.getCourseDescription(), instructor);

        return ResponseEntity.status(HttpStatus.CREATED).body("Course created successfully.");

    }


    @GetMapping
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public List<Course> getCoursesForInstructor() {
        // Fetch the currently logged-in user's ID from the security context
        String instructorId = SecurityContextHolder.getContext().getAuthentication().getName();
        return courseService.getCoursesByInstructor(instructorId);
    }


    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'INSTRUCTOR', 'ADMIN')")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }


    @GetMapping("/{courseId}")
    public Course getCourseById(@PathVariable String courseId) {
        return courseService.getCourseById(courseId);
    }

    @PostMapping("/{courseId}/lessons")
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public ResponseEntity<?> addLesson(@PathVariable String courseId, @RequestBody Lesson lesson) {
        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course doesn't exist.");
        }

        courseService.addLessonToCourse(courseId, lesson);
        return ResponseEntity.status(HttpStatus.CREATED).body(lesson);
    }


    @PostMapping("/{courseId}/enroll")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public ResponseEntity<?> enrollInCourse(@PathVariable String courseId, @RequestBody Student student, @RequestHeader("Authorization") String tokenHeader) {

        String token = tokenHeader.startsWith("Bearer ") ? tokenHeader.substring(7) : tokenHeader;
        String studentId;
        try {
            studentId = jwtService.extractUsername(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token.");
        }

        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course doesn't exist.");
        }

        if (!Objects.equals(student.getUserId(), studentId)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Student id doesn't match.");
        }

        if (courseService.checkStudentEnrollment(student.getUserId(), courseId)) {
            return ResponseEntity.status(HttpStatus.FOUND).body("You are already enrolled in this Course");
        }
        courseService.enrollCourse(courseId, student);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("You are have been enrolled successfully");
    }

    @GetMapping("/{courseId}/students")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<?> getEnrolledStudents(@PathVariable String courseId) {
        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course doesn't exist.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getEnrolledStudents(courseId));
        // return courseService.getEnrolledStudents(courseId);
    }

    @PostMapping("/{courseId}/lessons/{lessonId}/attend")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<String> attendLesson(
            @PathVariable String courseId,
            @PathVariable String lessonId,
            @RequestParam String otp,
            @RequestBody Student student
            , @RequestHeader("Authorization") String tokenHeader) {

        String token = tokenHeader.startsWith("Bearer ") ? tokenHeader.substring(7) : tokenHeader;
        String studentId;
        try {
            studentId = jwtService.extractUsername(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token.");
        }

        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course doesn't exist.");
        }

        if (!Objects.equals(student.getUserId(), studentId)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Student id doesn't match.");
        }

        if (!courseService.checkStudentEnrollment(student.getUserId(), courseId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("You have not enrolled in this Course");
        }

        Lesson lesson = courseService.getLessonInCourse(course, lessonId);
        if (lesson == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson doesn't exist.");
        }

        if (!lesson.validateOtp(otp)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP.");
        }

        try {
            courseService.attendLesson(courseId, student, lessonId, otp);
            return ResponseEntity.ok("Lesson attended successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while attending Lesson.");
        }
    }


}
