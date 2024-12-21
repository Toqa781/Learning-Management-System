package com.example.demo.Model.Assessments;

import com.example.demo.Model.Assessments.Questions.Question;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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

    private double assessmentGrade;

//    @Column(name = "assigned_date")
    private LocalDateTime assignedDate;

//    @Column(name = "deadline")
    private Date deadline;

    private String courseId;

    public Assessment(long id, String title, String description, Date deadline, double assessmentGrade) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.assessmentGrade = assessmentGrade;

        this.assignedDate=LocalDateTime.now();
    }

    public Assessment() {}

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

    public LocalDateTime getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(LocalDateTime assignedDate) {
        this.assignedDate = assignedDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public double getAssessmentGrade() {
        return assessmentGrade;
    }

    public void setAssessmentGrade(double grade) {
        this.assessmentGrade = grade;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
