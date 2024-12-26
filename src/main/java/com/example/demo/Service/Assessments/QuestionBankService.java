package com.example.demo.Service.Assessments;

import com.example.demo.Model.Assessments.Questions.Question;
import com.example.demo.Model.Assessments.Questions.QuestionBank;
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

    public QuestionBank getQuestionBankByCourseId(String courseId){
        return questionBankRepository.findByCourseId(courseId);
    }


    public void addQuestionsToBank(Long questionBankId , List<Question> newQuestions) {
        QuestionBank questionBank=getQuestionBankById(questionBankId);
        List<Question> questions = questionBank.getQuestionList();


//        for (Question newQuestion : newQuestions) {
//            boolean exists = false;
//
//            for (Question existingQuestion : questions) {
//                if (existingQuestion.getQuestionId()==(newQuestion.getQuestionId())) {
//                    exists = true;
//                    break;
//                }
//            }
//
//            if (exists) {
//                System.out.println("Question with ID " + newQuestion.getQuestionId() + " already exists. Not adding.");
//                newQuestions.remove(newQuestion);
//            }
//        }
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
