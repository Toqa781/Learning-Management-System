package com.example.demo.Model;

import jakarta.persistence.*;

@Entity
//@Table(name = "question")

public class Question {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;
//    @Column(name = "question")

    private String question;
//    @Column(name = "answer")

    private String correctAnswer;
//    @Column(name = "mark")

    private double questionMark;

    public Question(long ID, String question, String correctAnswer, double questionMark) {
        this.ID = ID;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.questionMark = questionMark;
    }

    public Question() {

    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public double getQuestionMark() {
        return questionMark;
    }

    public void setQuestionMark(double questionMark) {
        this.questionMark = questionMark;
    }
}
