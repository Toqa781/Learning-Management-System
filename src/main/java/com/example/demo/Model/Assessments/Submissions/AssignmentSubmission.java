package com.example.demo.Model.Assessments.Submissions;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
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


}
