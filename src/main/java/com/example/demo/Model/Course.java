package com.example.demo.Model;

import com.example.demo.Model.Assessments.Assignment;
import com.example.demo.Model.Assessments.Questions.QuestionBank;
import com.example.demo.Model.Users.Student;
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

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Lesson> lessons = new ArrayList<>();

    @OneToMany(mappedBy = "enrolledCourse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> enrolledStudents = new ArrayList<>();


    public Course() {}

    public Course(String courseId, String courseName, String courseDescription) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
    }

    public void addLesson(Lesson lesson){
        if(lesson!=null){
            lessons.add(lesson);
            lesson.setCourse(this);
        }
        else throw new IllegalArgumentException("Lesson cannot be null");
    }
    public void enrollStudent(Student student){
        if(!enrolledStudents.contains(student)){
            enrolledStudents.add(student);
            student.setEnrolledCourse(this);
        }
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



}

