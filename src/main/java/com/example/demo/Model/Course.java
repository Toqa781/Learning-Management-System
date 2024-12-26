package com.example.demo.Model;

import com.example.demo.Model.Assessments.Assignment;
import com.example.demo.Model.Assessments.Questions.QuestionBank;
import com.example.demo.Model.Users.Instructor;
import com.example.demo.Model.Users.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    @JsonIgnore
    private Instructor creator;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Lesson> lessons = new ArrayList<>();

    @OneToMany(mappedBy = "enrolledCourse", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Student> enrolledStudents = new ArrayList<>();



    public Course() {}

    public Course(String courseId, String courseName, String courseDescription,Instructor creator) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.creator=creator;
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

    public List<Student> getEnrolledStudents() {
        return new ArrayList<>(this.enrolledStudents); // Return a copy to prevent external modification
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

    public Instructor getCreator() {
        return creator;
    }

    public void setCreator(Instructor creator) {
        this.creator = creator;
    }
}

