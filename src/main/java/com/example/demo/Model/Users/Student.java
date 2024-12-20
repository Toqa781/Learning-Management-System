package com.example.demo.Model.Users;

import com.example.demo.Model.Course;
import jakarta.persistence.*;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Student extends User {

    // Correctly setting the parameter to the field
    @Setter
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course enrolledCourse;

    @ElementCollection
    private Set<String> attendedLessons = new HashSet<>();

    public Course getEnrolledCourse() {
        return enrolledCourse;
    }

    public Set<String> getAttendedLessons() {
        return attendedLessons;
    }

    public void attendLesson(String lessonId) {

    }

    public void setEnrolledCourse(Course course) {
    }
}
