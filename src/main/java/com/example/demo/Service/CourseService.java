package com.example.demo.Service;

import com.example.demo.Model.*;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;

import java.util.*;

public class CourseService {
    private Map<String, Course> courses = new HashMap<>();

    public void createCourse(String courseId, String courseName, String courseDescription) {
        Course course = new Course(courseId, courseName, courseDescription);
        courses.put(courseId, course);
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courses.values());
    }

    public Course getCourseById(String courseId) {
        return courses.get(courseId);
    }

    public void addLessonToCourse(String courseId, Lesson lesson) {
        Course course = courses.get(courseId);
        if (course != null) {
            course.getLessons().add(lesson);
        }
    }

    public void enrollCourse(Student student) {
        // Logic for student to enroll
    }

    public void viewAvailableCourse(Student student) {
        // View available courses for student
    }

    public void createCourse(Instructor instructor) {
        // Logic for instructor to create a course
    }
    public void addLesson(Instructor instructor) {
        // Logic for adding lessons
    }

    public void manageCourse(Admin admin) {
        // Admin-specific course management
    }

}

