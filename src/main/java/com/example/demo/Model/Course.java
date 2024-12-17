package com.example.demo.Model;
import java.util.*;

public class Course {
    String courseId;
    String courseName;
    String courseDescreption;
    private List<Lesson> lessons = new ArrayList<>();

    public Course() {}
    public Course(String courseId,String courseName,String courseDescreption){
        this.courseId=courseId;
        this.courseName=courseName;
        this.courseDescreption=courseDescreption;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseDescreption() {
        return courseDescreption;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }


}

