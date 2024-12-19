package com.example.demo.Model.Assessments.Submissions;

import jakarta.persistence.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
//@Table(name = "quizSubmission")

public class QuizSubmission extends Submission {
    //    @OneToMany(mappedBy = "submission", cascade= CascadeType.ALL)
    @ElementCollection
//    @CollectionTable(name = "quiz_submission_details", joinColumns = @JoinColumn(name = "submission_id"))
//    @MapKeyColumn(name = "student_id")
//    @Column(name = "submission_text")

    Map<Long, String> studentSubmission = new HashMap<>();

    public QuizSubmission(long studentID, long ID, Date submissionDate) {
        super(studentID, ID, submissionDate);
    }

    public QuizSubmission() {

    }


    public Map<Long, String> getStudentSubmission() {
        return studentSubmission;
    }

    public void setStudentSubmission(Map<Long, String> studentSubmission) {
        this.studentSubmission = studentSubmission;
    }
}
