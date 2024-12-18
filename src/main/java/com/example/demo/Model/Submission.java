package com.example.demo.Model;

import java.util.Date;

public abstract class Submission {
    private long studentID;
    private long ID;
    private double grade;
    private Date submissionDate;
    private boolean isGraded;

    public Submission(long studentID, long ID, Date submissionDate) {
        this.studentID = studentID;
        this.ID = ID;
        this.grade = -1;
        this.submissionDate = submissionDate;
        this.isGraded = false;
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
