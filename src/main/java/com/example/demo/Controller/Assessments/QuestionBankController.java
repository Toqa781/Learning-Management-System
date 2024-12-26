package com.example.demo.Controller.Assessments;

import com.example.demo.Model.Assessments.Questions.*;
import com.example.demo.Model.Course;
import com.example.demo.Service.Assessments.QuestionBankService;
import com.example.demo.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/courses/{courseId}/questionBank") //CHANGE
public class QuestionBankController {
    @Autowired
    private QuestionBankService questionBankService;
    @Autowired
    private CourseService courseService;

    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    @PostMapping("/createQuestionBank")
    public ResponseEntity<?> createQuestionBank(@PathVariable String courseId, @RequestBody QuestionBank questionBank) {
        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course doesn't exist.");
        }
        if (questionBankService.getQuestionBankByCourseId(courseId) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course already has a QuestionBank.");
        }

        questionBank.setCourseId(courseId);
        questionBankService.createQuestionBank(questionBank);

        return ResponseEntity.status(HttpStatus.CREATED).body(questionBank);
    }

    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    @PostMapping("/createQuestion")
    public ResponseEntity<?> createQuestions(@PathVariable String courseId, @RequestBody List<Question> questions) {
        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course doesn't exist.");
        }
        QuestionBank questionBank = questionBankService.getQuestionBankByCourseId(courseId);
//       QuestionBank questionBank = questionBankService.getQuestionBankById(question.getQuestionBankId());
        if (questionBank == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Question Bank doesn't exist, Create One First");
        }
        for (Question question : questions) {

            if (question instanceof MCQ mcq) {
                if (mcq.getOptions() == null || mcq.getOptions().isEmpty()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("MCQ should have options.");
                }
//                ((MCQ) question).setCorrectAnswer(((MCQ) question).getCorrectAnswer());
//                ((MCQ) question).setOptions(((MCQ) question).getOptions());
            } else if (question instanceof Essay essay) {
                if (essay.getCorrectAnswer() == null || essay.getCorrectAnswer().isEmpty()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Essay should have a correct answer.");
                }
                ((Essay) question).setCorrectAnswer(((Essay) question).getCorrectAnswer());
            } else if (question instanceof TrueOrFalse trueOrFalse) {
                if (trueOrFalse.getCorrectAnswer() == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("True or False should have a correct answer.");
                }
//                ((TrueOrFalse) question).setCorrectAnswer(((TrueOrFalse) question).getCorrectAnswer());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unknown Question type.");
            }
        }

        questionBankService.addQuestionsToBank(questionBank.getQuestionBankId(), questions);
        return ResponseEntity.status(HttpStatus.CREATED).body(questionBank);
    }

}
