package com.example.demo.Model;

import java.util.Date;

public class AssignmentSubmission extends Submission {
    private String fileURL;
    public AssignmentSubmission(long studentID, long ID, Date submissionDate, String fileURL) {
        super(studentID, ID, submissionDate);
        this.fileURL = fileURL;
    }

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }
}
