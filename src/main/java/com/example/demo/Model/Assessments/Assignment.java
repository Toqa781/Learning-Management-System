package com.example.demo.Model.Assessments;

import com.example.demo.Model.Assessments.Questions.Question;
import com.example.demo.Model.Assessments.Submissions.Submission;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity

public class Assignment extends Assessment {

    @OneToMany
    private List<Submission>submissionList;

    @OneToMany
    private List<Question> assignmentQuestions;

    public Assignment(long id, String title, String description, Date assignedDate, Date deadline, String courseId, double grade , List<Question> assignmentQuestions) {
        super(id, title, description, assignedDate, deadline, grade , courseId);
        this.assignmentQuestions=assignmentQuestions;
    }

    public Assignment(){}

    public List<Submission> getSubmissionList() {
        return submissionList;
    }

    public void setSubmissionList(List<Submission> submissionList) {
        this.submissionList = submissionList;
    }

    public List<Question> getAssignmentQuestions() {
        return assignmentQuestions;
    }

    public void setAssignmentQuestions(List<Question> assignmentQuestions) {
        this.assignmentQuestions = assignmentQuestions;
    }
}
