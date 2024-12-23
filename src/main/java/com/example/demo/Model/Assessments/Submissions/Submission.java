package com.example.demo.Model.Assessments.Submissions;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@Entity
//@Table(name = "submission")

public abstract class Submission {

//    @Column(name = "student_id")
    private String studentID;
    //    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID; //submission id

    private long assessmentId; //quizId, assi

    //    @Column(name = "grade")
    private double grade;

    //    @Column(name = "subDate")
    private LocalDateTime submissionDate;

    //    @Column(name = "isGraded")
    private boolean isGraded;

    public Submission(String studentID, long assessmentId, LocalDateTime submissionDate) {
        this.studentID = studentID;
        this.grade = -1;
        this.submissionDate = submissionDate;
        this.isGraded = false;
        this.assessmentId= assessmentId;
    }

    public Submission() {

    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public LocalDateTime getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }

    public boolean isGraded() {
        return isGraded;
    }

    public void setGraded(boolean graded) {
        isGraded = graded;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

}
