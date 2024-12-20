package com.example.demo.Service.Assessments;

import com.example.demo.Model.Assessments.Questions.Question;
import com.example.demo.Model.Assessments.Questions.QuestionBank;
import com.example.demo.Model.Course;
import com.example.demo.Repository.Assesments.Questions.QuestionBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionBankService {

    @Autowired
    private final QuestionBankRepository questionBankRepository;

    public QuestionBankService(QuestionBankRepository questionBankRepository) {
        this.questionBankRepository = questionBankRepository;
    }

    public void addQuestionsToBank(Long questionBankId , List<Question> newQuestions) {
        QuestionBank questionBank=getQuestionBankById(questionBankId);
        List<Question> questions = questionBank.getQuestionList();
        questions.addAll(newQuestions);
        questionBank.setQuestionList(questions);
        questionBankRepository.save(questionBank);
    }

    public void createQuestionBank(QuestionBank questionBank){
        questionBankRepository.save(questionBank);
    }
    public QuestionBank getQuestionBankById(Long questionBankId) {
        return  questionBankRepository.findById(questionBankId)
                .orElse(null);
    }




}
