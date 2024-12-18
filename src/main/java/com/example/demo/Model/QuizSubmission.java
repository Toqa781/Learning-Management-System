package com.example.demo.Model;

import java.util.Date;
import java.util.Map;

public class QuizSubmission extends Submission{
    Map<Long,String>studentSubmission;
    public QuizSubmission(long studentID, long ID, Date submissionDate) {
        super(studentID, ID, submissionDate);
    }

    public Map<Long, String> getStudentSubmission() {
        return studentSubmission;
    }

    public void setStudentSubmission(Map<Long, String> studentSubmission) {
        this.studentSubmission = studentSubmission;
    }
}
