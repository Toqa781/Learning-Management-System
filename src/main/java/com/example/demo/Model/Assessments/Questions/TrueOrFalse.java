package com.example.demo.Model.Assessments.Questions;

public class TrueOrFalse extends Question{

    Boolean correctAnswer;
    public TrueOrFalse(long ID, String question, double questionMark ,Long questionBankId,  Boolean correctAnswer) {
        super(ID, question, questionMark , questionBankId);
        this.correctAnswer=correctAnswer;
    }
}
