package com.example.demo.Model.Assessments.Questions;

public class Essay extends Question {
    //    @Column(name = "answer")
    private String correctAnswer;

    public Essay(long ID, String question, double questionMark, String correctAnswer , Long questionBankId) {
        super(ID, question, questionMark , questionBankId);
        this.correctAnswer = correctAnswer;
    }
}
