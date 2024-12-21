package com.example.demo.Model.Assessments.Questions;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;
@Entity

public class MCQ extends Question{
    @ElementCollection
    private List<String> options;

    //    @Column(name = "answer")
    private String correctAnswer;

    public MCQ(long ID, String question, double questionMark, List<String> options , String correctAnswer) {
        super(ID, question, questionMark);
        this.options = options;
        this.correctAnswer=correctAnswer;
    }

    public MCQ() {

    }


    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
