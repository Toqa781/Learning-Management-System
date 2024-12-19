package com.example.demo.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;
@Entity
//@Table(name = "submission")

public abstract class Submission {
    @Id
//    @Column(name = "student_id")

    private long studentID;
//    @Column(name = "id")

    private long ID;
//    @Column(name = "grade")
    private double grade;
//    @Column(name = "subDate")
    private Date submissionDate;
//    @Column(name = "isGraded")
    private boolean isGraded;

    public Submission(long studentID, long ID, Date submissionDate) {
        this.studentID = studentID;
        this.ID = ID;
        this.grade = -1;
        this.submissionDate = submissionDate;
        this.isGraded = false;
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

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public boolean isGraded() {
        return isGraded;
    }

    public void setGraded(boolean graded) {
        isGraded = graded;
    }

    public long getStudentID() {
        return studentID;
    }

    public void setStudentID(long studentID) {
        this.studentID = studentID;
    }


}
