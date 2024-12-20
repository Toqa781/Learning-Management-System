package com.example.demo.Service.Assessments;

import com.example.demo.Model.Course;
import com.example.demo.Model.Assessments.Questions.Question;
import com.example.demo.Model.Assessments.Quiz;
import com.example.demo.Repository.Assesments.Questions.QuestionBankRepository;
import com.example.demo.Repository.Assesments.QuizRepository;
import com.example.demo.Repository.Assesments.Submissions.QuizSubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static java.lang.Math.min;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizSubmissionRepository quizSubmissionRepository;

    @Autowired
    private QuestionBankRepository questionBankRepository;


    public Quiz saveQuiz(Quiz quiz,Course course) {
        quiz.setQuizQuestions(createQuizQuestions(quiz.getNumberOfQuestions(),questionBankRepository.findByCourseId(course.getCourseId()).getQuestionList()));
        return quizRepository.save(quiz);
    }

    private List<Question> createQuizQuestions(int numberOfQuestions, List<Question> questions) {
        Collections.shuffle(questions);
        int size = min(numberOfQuestions, questions.size());
        return questions.subList(0, size);
    }
}
