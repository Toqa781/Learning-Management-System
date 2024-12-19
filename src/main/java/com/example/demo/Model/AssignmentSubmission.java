package com.example.demo.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Date;
@Entity
//@Table(name = "assignmentSubmission")

public class AssignmentSubmission extends Submission {
//    @Column(name = "fileURL")

    private String fileURL;


    public AssignmentSubmission(long studentID, long ID, Date submissionDate, String fileURL) {
        super(studentID, ID, submissionDate);
        this.fileURL = fileURL;
    }

    public AssignmentSubmission() {

    }


    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }
}
