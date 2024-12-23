package com.example.demo.Model.Assessments.Submissions;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
public class QuizSubmission extends Submission {

    @ElementCollection
    List<Answer> studentAnswers;

    public QuizSubmission(String studentID,long assessmentId, LocalDateTime submissionDate) {
        super(studentID,assessmentId ,submissionDate);
    }

    public QuizSubmission() {
    }
    public void setStudentSubmission (List<Answer> studentSubmission) {
        this.studentAnswers = studentSubmission;
    }

    @Embeddable
    public static class Answer {
        private String stringAnswer;
        private Boolean booleanAnswer;

        public Answer() {
        }
        public Answer(String stringAnswer) {
            this.stringAnswer = stringAnswer;
        }
        public Answer(Boolean booleanAnswer) {
            this.booleanAnswer = booleanAnswer;
        }

        public String getStringAnswer() {
            return stringAnswer;
        }

        public void setStringAnswer(String stringAnswer) {
            this.stringAnswer = stringAnswer;
        }

        public Boolean getBooleanAnswer() {
            return booleanAnswer;
        }

        public void setBooleanAnswer(Boolean booleanAnswer) {
            this.booleanAnswer = booleanAnswer;
        }
        public boolean isString() {
            return stringAnswer != null;
        }
        public boolean isBoolean() {
            return booleanAnswer != null;
        }
    }
}
