package com.example.demo.Controller;
import com.example.demo.Model.*;
import com.example.demo.Model.Users.Instructor;
import com.example.demo.Model.Users.Student;
import com.example.demo.Service.*;
import com.example.demo.Service.Authentication.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    // Constructor-based Dependency Injection
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public void createCourse(@RequestBody Course course) {
        String instructorId = SecurityContextHolder.getContext().getAuthentication().getName();
        Instructor instructor = new Instructor(); // Assuming you have a method to fetch the Instructor object
        instructor.setUserId(instructorId);
        course.setCreator(instructor);
        courseService.createCourse(course.getCourseId(), course.getCourseName(), course.getCourseDescription(), instructor);
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
    public void addLesson(@PathVariable String courseId, @RequestBody Lesson lesson) {
        courseService.addLessonToCourse(courseId, lesson);
    }

    @PostMapping("/{courseId}/enroll")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public void enrollInCourse(@PathVariable String courseId, @RequestBody Student student) {
        courseService.enrollCourse(courseId, student);
    }

    @GetMapping("/{courseId}/students")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    public List<Student> getEnrolledStudents(@PathVariable String courseId) {
        return courseService.getEnrolledStudents(courseId);
    }

    @PostMapping("/{courseId}/lessons/{lessonId}/attend")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public void attendLesson(@PathVariable String courseId, @PathVariable String lessonId, @RequestParam String otp, @RequestBody Student student) {
        courseService.attendLesson(courseId, student, lessonId, otp);
    }
}
