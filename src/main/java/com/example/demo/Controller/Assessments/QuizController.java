package com.example.demo.Controller.Assessments;

import com.example.demo.Model.Course;
import com.example.demo.Model.Assessments.Quiz;
import com.example.demo.Service.CourseService;
import com.example.demo.Service.Assessments.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/courses/{courseId}/quiz") //CHANGE

public class QuizController {
    @Autowired
    private QuizService quizService;

    @Autowired
    private CourseService courseService;

    @PostMapping("/createQuiz")
    public ResponseEntity<Quiz> createQuiz(@PathVariable String courseId, @RequestBody Quiz quiz) {
        Course course = courseService.getCourseById(courseId);
        Quiz createdQuiz = quizService.saveQuiz(quiz, course);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuiz);
    }
}
