package com.example.demo.Model;


import jakarta.persistence.*;

import java.util.UUID;

import com.example.demo.Model.Users.Instructor;
import com.example.demo.Model.Users.Student;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
//@Table(name = "lessons")
public class Lesson {
    @Id
    private String lessonId;
    private String lessonName;
    private String OTP;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Lesson() {
    }

    public Lesson(String lessonId, String lessonName) {
        if (lessonId == null || lessonName == null || lessonId.isEmpty() || lessonName.isEmpty()) {
            throw new IllegalArgumentException("Lesson ID and name cannot be null or empty");
        }
        this.lessonId = lessonId;
        this.lessonName = lessonName;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public boolean validateOtp(String otp) {
        return this.OTP.equals(otp);
    }

    public String getLessonId() {
        return lessonId;
    }

    public String getLessonName() {
        return lessonName;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOtp(String otp) {
        this.OTP = otp;
    }
}

