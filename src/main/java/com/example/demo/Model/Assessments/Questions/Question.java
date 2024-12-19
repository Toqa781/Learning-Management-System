package com.example.demo.Model.Assessments.Questions;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
//@Table(name = "question")

public class Question {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long questionId;

    //    @Column(name = "question")
    private String question;

    //    @Column(name = "mark")
    private double questionMark;

    private Long questionBankId;


    public Question(long questionId, String question, double questionMark, Long questionBankId) {
        this.questionId = questionId;
        this.question = question;
        this.questionMark = questionMark;
        this.questionBankId = questionBankId;
    }

    public Question() {
    }

}
