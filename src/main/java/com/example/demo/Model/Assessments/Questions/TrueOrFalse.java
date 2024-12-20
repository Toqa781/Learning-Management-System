package com.example.demo.Model.Assessments.Questions;

import jakarta.persistence.Entity;

@Entity
public class TrueOrFalse extends Question{

    Boolean correctAnswer;
    public TrueOrFalse(long ID, String question, double questionMark ,  Boolean correctAnswer) {
        super(ID, question, questionMark);
        this.correctAnswer=correctAnswer;
    }

    public TrueOrFalse() {

    }

    public Boolean getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
