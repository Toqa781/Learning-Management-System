package com.example.demo.Model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
//@Table(name = "lessons")
public class Lesson {
    @Id
//    @Column(name = "lessonId")

    String lessonId;
//    @Column(name = "lessonName")

    String lessonName;
//    @Column(name = "otp")

    String OTP;

    public Lesson(){}

    public Lesson(String lessonId,String lessonName,String OTP){
        this.lessonId=lessonId;
        this.lessonName=lessonName;
        this.OTP=OTP;
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

    public void attendLesson(Student student, String OTP) {
        // Logic for students to attend the lesson
    }

    public void generateOTP(Instructor instructor) {
        // OTP generation logic
    }
}
