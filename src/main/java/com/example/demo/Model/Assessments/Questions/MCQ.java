package com.example.demo.Model.Assessments.Questions;

import java.util.List;

public class MCQ extends Question{
    private List<String> options;

    //    @Column(name = "answer")
    private String correctAnswer;

    public MCQ(long ID, String question, double questionMark, List<String> options ,Long questionBankId, String correctAnswer) {
        super(ID, question, questionMark , questionBankId);
        this.options = options;
        this.correctAnswer=correctAnswer;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
