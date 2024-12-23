package com.example.demo.Model.Assessments;

public class Grading {
    private String studentId;

    private double grade;

    private long assessmentId;

    public Grading(String studentId, double grade, long assessmentId) {
        this.studentId = studentId;
        this.grade = grade;
        this.assessmentId = assessmentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public long getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(long assessmentId) {
        this.assessmentId = assessmentId;
    }


}
