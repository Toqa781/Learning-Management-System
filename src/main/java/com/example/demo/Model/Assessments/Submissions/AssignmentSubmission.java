package com.example.demo.Model.Assessments.Submissions;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@Entity
//@Table(name = "assignmentSubmission")

public class AssignmentSubmission extends Submission {
    //    @Column(name = "fileURL")
    @Lob
    @Column(name = "file_content", columnDefinition = "BLOB")
    private byte[] fileContent;

//    private long assessmentId;

    public AssignmentSubmission(String studentID, long assessmentId, LocalDateTime submissionDate, byte[] fileContent) {
        super(studentID, assessmentId, submissionDate);
        this.fileContent = fileContent;
    }

    public AssignmentSubmission() {
    }

}
