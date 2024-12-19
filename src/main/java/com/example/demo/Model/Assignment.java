package com.example.demo.Model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity

public class Assignment extends Assessment{

    @OneToMany

    private List<Submission>submissionList;

//    public Assignment(long id, String title, String description, Date assignedDate, Date deadline, Course course, double grade) {
//        super(id, title, description, assignedDate, deadline, course, grade);
//    }

    public Assignment() {

    }

    public List<Submission> getSubmissionList() {
        return submissionList;
    }

    public void setSubmissionList(List<Submission> submissionList) {
        this.submissionList = submissionList;
    }
}
