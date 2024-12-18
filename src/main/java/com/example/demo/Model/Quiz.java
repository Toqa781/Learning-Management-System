package com.example.demo.Model;

import java.util.Date;
import java.util.List;

public class Quiz extends Assessment{
    private List<Question> quizQuestions;
    public Quiz(String id, String title, String description, Date assignedDate, Date deadline, Course course, double grade) {
        super(id, title, description, assignedDate, deadline, course, grade);
    }

    public List<Question> getQuizQuestions() {
        return quizQuestions;
    }

    public void setQuizQuestions(List<Question> quizQuestions) {
        this.quizQuestions = quizQuestions;
    }
}
