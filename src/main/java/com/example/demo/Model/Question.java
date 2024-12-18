package com.example.demo.Model;

public class Question {
    private long ID;
    private String question;
    private String correctAnswer;
    private double questionMark;

    public Question(long ID, String question, String correctAnswer, double questionMark) {
        this.ID = ID;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.questionMark = questionMark;
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
