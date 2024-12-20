package com.example.demo.Model.Assessments.Questions;

import jakarta.persistence.Entity;

@Entity
public class Essay extends Question {
    //    @Column(name = "answer")
    private String correctAnswer;

    public Essay(long ID, String question, double questionMark, String correctAnswer) {
        super(ID, question, questionMark);
        this.correctAnswer = correctAnswer;
    }

    public Essay() {

    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
