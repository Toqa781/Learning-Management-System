package com.example.demo.Service;

import com.example.demo.Model.Course;
import com.example.demo.Model.Question;
import com.example.demo.Model.Quiz;
import com.example.demo.Repository.QuestionRepository;
import com.example.demo.Repository.QuizRepository;
import com.example.demo.Repository.QuizSubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.min;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizSubmissionRepository quizSubmissionRepository;


    public Quiz saveQuiz(Quiz quiz,Course course) {
        quiz.setQuizQuestions(createQuizQuestions(quiz.getNumberOfQuestions(),course.getQuestionBank()));
        return quizRepository.save(quiz);
    }

    public List<Question> createQuizQuestions(int numberOfQuestions, List<Question> questions) {
        Collections.shuffle(questions);
        int size = min(numberOfQuestions, questions.size());
        return questions.subList(0, size);
    }
}
