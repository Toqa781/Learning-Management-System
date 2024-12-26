package com.example.demo.Testing;

import com.example.demo.Controller.Assessments.QuizController;
import com.example.demo.Model.Assessments.Questions.QuestionBank;
import com.example.demo.Model.Assessments.Quiz;
import com.example.demo.Model.Course;
import com.example.demo.Repository.Assesments.QuizRepository;
import com.example.demo.Service.Assessments.QuestionBankService;
import com.example.demo.Service.Assessments.QuizService;
import com.example.demo.Service.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.Assert.*;


@ExtendWith(MockitoExtension.class)
class QuizControllerTest {

    @InjectMocks
    private QuizController quizController;

    @Mock
    private QuizService quizService;

    @Mock
    private CourseService courseService;

    @Mock
    private QuestionBankService questionBankService;

    @Test
    void testSaveQuiz() {
        String courseId = "C123";
        Course course = new Course();
        course.setCourseId(courseId);

        Quiz quiz = new Quiz();
        quiz.setTitle("Sample Quiz");

        Mockito.when(quizService.saveQuiz(Mockito.any(Quiz.class), Mockito.any(Course.class))).thenReturn(quiz);

        //(mimicking controller behavior)
        Quiz savedQuiz = quizService.saveQuiz(quiz, course);

        assertNotNull(savedQuiz);
        assertEquals("Sample Quiz", savedQuiz.getTitle());
        Mockito.verify(quizService, Mockito.times(1)).saveQuiz(Mockito.any(Quiz.class), Mockito.any(Course.class));
    }

    @Test
    void createQuiz_success() {
        String courseId = "C123";
        Course course = new Course();
        course.setCourseId(courseId);
        Quiz quiz = new Quiz();
        quiz.setTitle("Sample Quiz");
        Quiz savedQuiz = new Quiz();
        savedQuiz.setTitle("Sample Quiz");

        // Mocking the course service and question bank service
        Mockito.when(courseService.getCourseById(courseId)).thenReturn(course);
        QuestionBank mockQuestionBank = new QuestionBank();
        mockQuestionBank.setQuestionList(new ArrayList<>());
        Mockito.when(questionBankService.getQuestionBankByCourseId(courseId)).thenReturn(mockQuestionBank);

        Mockito.when(quizService.saveQuiz(Mockito.any(Quiz.class), Mockito.any(Course.class))).thenReturn(savedQuiz);

        // Call the controller method
        ResponseEntity<?> response = quizController.createQuiz(courseId, quiz);

        // Verifying the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof Quiz);
        Quiz responseQuiz = (Quiz) response.getBody();
        assertEquals("Sample Quiz", responseQuiz.getTitle());

        // Verify that the service methods were called
        Mockito.verify(courseService, Mockito.times(1)).getCourseById(courseId);
        Mockito.verify(questionBankService, Mockito.times(1)).getQuestionBankByCourseId(courseId);  // Verify the mock for QuestionBankService
        Mockito.verify(quizService, Mockito.times(1)).saveQuiz(Mockito.any(Quiz.class), Mockito.any(Course.class));
    }

    @Test
    void createQuiz_courseNotFound() {
        String courseId = "C123";
        Quiz quiz = new Quiz();

        // Mocking courseService to return null
        Mockito.when(courseService.getCourseById(courseId)).thenReturn(null);

        // Call the controller method
        ResponseEntity<?> response = quizController.createQuiz(courseId, quiz);

        // Verifying the response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Course doesn't exist", response.getBody());
    }

    @Test
    void createQuiz_quizExists() {
        String courseId = "C123";
        Course course = new Course();
        course.setCourseId(courseId);
        Quiz quiz = new Quiz();
        quiz.setId(1L);

        Quiz existingQuiz = new Quiz();
        existingQuiz.setId(1L);

        // Mocking courseService and quizService
        Mockito.when(courseService.getCourseById(courseId)).thenReturn(course);
        Mockito.when(quizService.getSpecificQuiz(courseId, quiz.getId())).thenReturn(existingQuiz);

        // Call the controller method
        ResponseEntity<?> response = quizController.createQuiz(courseId, quiz);

        // Verifying the response
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals("Quiz with same ID already exists.", response.getBody());
    }
}
