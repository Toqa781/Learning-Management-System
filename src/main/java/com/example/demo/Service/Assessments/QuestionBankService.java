package com.example.demo.Service.Assessments;

import com.example.demo.Model.Assessments.Questions.Question;
import com.example.demo.Model.Assessments.Questions.QuestionBank;
import com.example.demo.Repository.Assesments.Questions.QuestionBankRepository;

import java.util.List;

public class QuestionBankService {
    private final QuestionBankRepository questionBankRepository;

    public QuestionBankService(QuestionBankRepository questionBankRepository) {
        this.questionBankRepository = questionBankRepository;
    }

    public void addQuestionToBank(Long questionBankId , Question question) {
        QuestionBank questionBank=questionBankRepository.findById(questionBankId).orElseThrow(() -> new IllegalArgumentException("Question Bank not Found"));

        List<Question> questions = questionBank.getQuestionList();
        questions.add(question);
        questionBank.setQuestionList(questions);
        questionBankRepository.save(questionBank);
    }

}
