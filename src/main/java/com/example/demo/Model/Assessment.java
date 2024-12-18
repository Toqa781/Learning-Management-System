package com.example.demo.Model;

import java.util.Date;

public abstract class Assessment {

    private String id;
    private String title;
    private String description;
    private Date assignedDate;
    private Date deadline;
    private Course course;

    public Assessment(String id, String title, String description, Date assignedDate, Date deadline, Course course, double grade) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.assignedDate = assignedDate;
        this.deadline = deadline;
        this.course = course;
        this.grade = grade;
    }

    public String getId() {
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public void setId(String id) {
        this.id = id;
    }

    double grade;




}
