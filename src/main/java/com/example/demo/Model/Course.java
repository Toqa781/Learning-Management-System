package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Setter
@Getter
public class Course {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "course_id")

    String courseId;
//    @Column(name = "course_name")

    String courseName;
//    @Column(name = "courseDescription")

    String courseDescription;
    @OneToMany

    private List<Lesson> lessons = new ArrayList<>();
    @OneToMany

    private List<Question> questionBank = new ArrayList<>();


    public Course() {
    }

    public Course(String courseId, String courseName, String courseDescreption) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDescription = courseDescreption;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public List<Question> getQuestionBank() {
        return questionBank;
    }

    public void setQuestionBank(List<Question> quizQuestions) {
        this.questionBank = quizQuestions;
    }
}

