package com.example.demo.Controller;

import com.example.demo.Model.Course;
import com.example.demo.Model.Quiz;
import com.example.demo.Service.CourseService;
import com.example.demo.Service.QuizService;
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

    private final CourseService courseService = new CourseService();

    @PostMapping("/createQuiz")
    public ResponseEntity<Quiz> createQuiz(@PathVariable String courseId, @RequestBody Quiz quiz) {
        Course course = courseService.getCourseById(courseId);
        Quiz createdQuiz = quizService.saveQuiz(quiz, course);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuiz);
    }
}
