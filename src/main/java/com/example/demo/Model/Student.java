package com.example.demo.Model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Student extends User {

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course enrolledCourse;

    @ElementCollection
    private Set<String> attendedLessons = new HashSet<>();

    public Course getEnrolledCourse() {
        return enrolledCourse;
    }

    public void setEnrolledCourse(Course course) {
        this.enrolledCourse = course; // Correctly setting the parameter to the field
    }

    public Set<String> getAttendedLessons() {
        return attendedLessons;
    }

    public void attendLesson(String lessonId) {
        if (!attendedLessons.contains(lessonId)) {
            attendedLessons.add(lessonId);
            System.out.println("Lesson " + lessonId + " marked as attended.");
        } else {
            System.out.println("Lesson " + lessonId + " is already marked as attended.");
        }
    }
}
