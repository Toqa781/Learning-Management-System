package com.example.demo.Model.Assessments;

import com.example.demo.Model.Assessments.Questions.Question;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
//@Table(name = "quiz")
public class Quiz extends Assessment {
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Question> quizQuestions;

    //    @Column(name = "numberOfQs")
    int numberOfQuestions;

    public Quiz() {}

    public Quiz(Long id, String title, String description, LocalDateTime deadline, double grade, int numberOfQuestions) {
        super(id, title, description, deadline, grade) ;
        this.numberOfQuestions = numberOfQuestions;

    }

    public List<Question> getQuizQuestions() {
        return quizQuestions;
    }

    public void setQuizQuestions(List<Question> quizQuestions) {
        this.quizQuestions = quizQuestions;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }
}
