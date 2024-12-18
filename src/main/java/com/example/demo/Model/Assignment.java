package com.example.demo.Model;

import java.util.Date;
import java.util.List;

public class Assignment extends Assessment{
    private List<Submission>submissionList;

    public Assignment(String id, String title, String description, Date assignedDate, Date deadline, Course course, double grade) {
        super(id, title, description, assignedDate, deadline, course, grade);
    }

    public List<Submission> getSubmissionList() {
        return submissionList;
    }

    public void setSubmissionList(List<Submission> submissionList) {
        this.submissionList = submissionList;
    }
}
