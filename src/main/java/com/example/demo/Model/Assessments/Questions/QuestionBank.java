package com.example.demo.Model.Assessments.Questions;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class QuestionBank {
    @Id
    private Long questionBankId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questionList;

    private String courseId;


    public QuestionBank(Long questionBankId) {
        this.questionBankId = questionBankId;
    }

    public QuestionBank() {

    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setQuestionBankId(Long questionBankId) {
        this.questionBankId = questionBankId;
    }

    public Long getQuestionBankId() {
        return questionBankId;
    }
}
