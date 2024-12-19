package com.example.demo.Model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
//@Table(name = "assessment")
public abstract class Assessment {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "assessment_id")

    private long id;
//    @Column(name = "title")

    private String title;
//    @Column(name = "description")
    private String description;
//    @Column(name = "assigned_date")
    private Date assignedDate;
//    @Column(name = "deadline")
    private Date deadline;
//    private Course course;

    public Assessment(long id, String title, String description, Date assignedDate, Date deadline,  double grade) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.assignedDate = assignedDate;
        this.deadline = deadline;
//        this.course = course;
        this.grade = grade;
    }

    public Assessment() {

    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(Date assignedDate) {
        this.assignedDate = assignedDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

//    public Course getCourse() {
//        return course;
//    }
//
//    public void setCourse(Course course) {
//        this.course = course;
//    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public void setId(long id) {
        this.id = id;
    }

    double grade;




}
