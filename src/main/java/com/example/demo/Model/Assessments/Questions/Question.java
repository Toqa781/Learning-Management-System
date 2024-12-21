package com.example.demo.Model.Assessments.Questions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


//@Table(name = "question")
@Entity
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Essay.class, name = "essay"),
        @JsonSubTypes.Type(value = MCQ.class, name = "mcq"),
        @JsonSubTypes.Type(value = TrueOrFalse.class, name = "trueorfalse")
})public class Question {
    @Id
    private long questionId;

    //    @Column(name = "question")
    private String question;

    //    @Column(name = "mark")
    private double questionMark;

    private Long questionBankId;


    public Question(long questionId, String question, double questionMark) {
        this.questionId = questionId;
        this.question = question;
        this.questionMark = questionMark;
    }

    public Question() {
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public double getQuestionMark() {
        return questionMark;
    }

    public void setQuestionMark(double questionMark) {
        this.questionMark = questionMark;
    }

    public Long getQuestionBankId() {
        return questionBankId;
    }

    public void setQuestionBankId(Long questionBankId) {
        this.questionBankId = questionBankId;
    }
}
