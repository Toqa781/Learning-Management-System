package com.example.demo.Service;

import com.example.demo.Model.*;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseService {
    private final Map<String, Course> courses = new HashMap<>();

    public void createCourse(String courseId, String courseName, String courseDescription) {
        Course course = new Course(courseId, courseName, courseDescription);
        courses.put(courseId, course);
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courses.values());
    }

    public Course getCourseById(String courseId) {
        return Optional.ofNullable(courses.get(courseId))
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
    }

    public void addLessonToCourse(String courseId, Lesson lesson) {
        Course course = getCourseById(courseId); // Ensures course exists
        course.addLesson(lesson);
    }

    public void enrollCourse(String courseId, Student student) {
        Course course = getCourseById(courseId); // Ensures course exists
        course.enrollStudent(student);
    }

    public List<Student> getEnrolledStudents(String courseId) {
        Course course = getCourseById(courseId); // Ensures course exists
        return course.getEnrolledStudents();
    }

    public void attendLesson(String courseId, Student student, String lessonId, String otp) {
        Course course = getCourseById(courseId); // Ensures course exists
        Lesson lesson = course.getLessons().stream()
                .filter(l -> l.getLessonId().equals(lessonId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found"));

        if (lesson.validateOtp(otp)) {
            student.attendLesson(lessonId);
        } else {
            throw new IllegalArgumentException("Invalid OTP");
        }
    }
}

