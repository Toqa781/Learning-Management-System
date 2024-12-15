package com.example.demo.Model;



public class Lesson {
    String lessonId;
    String lessonName;
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
}
