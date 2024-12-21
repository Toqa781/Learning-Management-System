package com.example.demo.Model.Users;

import com.example.demo.Model.Course;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("STUDENT")
public class Student extends User {

    //add any specific fields
    public Student() {
        super();
    }

    // Correctly setting the parameter to the field
    @Setter
    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonBackReference
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
    public Student(Long userId, String username, String email, String password) {
        super(String.valueOf(userId), username, email, password, "STUDENT");
    }
}
