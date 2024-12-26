package com.example.demo.Controller.Assessments;

import com.example.demo.Model.Assessments.Questions.*;
import com.example.demo.Model.Assessments.Submissions.QuizSubmission;
import com.example.demo.Model.Course;
import com.example.demo.Model.Assessments.Quiz;
import com.example.demo.Model.Users.Student;
import com.example.demo.Service.Assessments.QuestionBankService;
import com.example.demo.Service.Authentication.JWTService;
import com.example.demo.Service.CourseService;
import com.example.demo.Service.Assessments.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("/courses/{courseId}/quiz") //CHANGE

public class QuizController {
    @Autowired
    private QuizService quizService;

    @Autowired
    private QuestionBankService questionBankService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private JWTService jwtService;

    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    @PostMapping("/createQuiz")
    public ResponseEntity<?> createQuiz(@PathVariable String courseId, @RequestBody Quiz quiz) {
        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course doesn't exist");
        }

        Quiz quizCheck = quizService.getSpecificQuiz(courseId, quiz.getId());
        if (quizCheck != null) {
            return ResponseEntity.status(HttpStatus.FOUND).body("Quiz with same ID already exists.");
        }

        QuestionBank qb = questionBankService.getQuestionBankByCourseId(courseId);
        if (qb == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question Bank doesn't exist");
        }

        if(qb.getQuestionList().size() < quiz.getNumberOfQuestions()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not enough questions in the question bank");
        }

        Quiz createdQuiz = quizService.saveQuiz(quiz, course);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuiz);
    }

    @GetMapping("/{quizId}/getQuiz")
    public ResponseEntity<?> getQuiz(@PathVariable String courseId, @PathVariable Long quizId) {

        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course doesn't exist");
        }

        Quiz quiz = quizService.getSpecificQuiz(courseId, quizId);
        if (quiz != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(quiz);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No quiz found with this ID.");

    }
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    @GetMapping("/getQuizes")
    public ResponseEntity<?> getQuiz(@PathVariable String courseId) {
        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course doesn't exist");
        }

        List<Quiz> quizList = quizService.getQuizesInCourse(courseId);
        if (!quizList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(quizList);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No quizes in course.");

    }

    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    @GetMapping("/quizes/getSubmissions")
    public ResponseEntity<?> getAllSub (@PathVariable String courseId){
       List <Quiz> quizes = quizService.getQuizesInCourse(courseId);
       List<List<QuizSubmission>>subs = new ArrayList<>();
       for (Quiz quiz: quizes){
           subs.add(quizService.getAllStudentsQuizSubmissions(quiz.getId()));
       }
        return ResponseEntity.status(HttpStatus.CREATED).body(subs);
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @PostMapping("/{quizId}/submitQuiz")
    public ResponseEntity<?> submitQuiz(@PathVariable String courseId, @PathVariable Long quizId ,@RequestBody QuizSubmission quizSubmission , @RequestHeader("Authorization") String tokenHeader) {

        String token = tokenHeader.startsWith("Bearer ") ? tokenHeader.substring(7) : tokenHeader;
        String studentId;
        try {
            studentId = jwtService.extractUsername(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token.");
        }

        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course doesn't exist.");
        }

        if(!courseService.checkStudentEnrollment(studentId,courseId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("You have not enrolled in this Course");
        }

        if(!Objects.equals(studentId, quizSubmission.getStudentID())){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Student id doesn't match.");
        }

        Quiz quiz = quizService.getSpecificQuiz(courseId, quizId);
        if (quiz == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No quiz found with this ID.");
        }
        if (quiz.getQuizQuestions().size() != quizSubmission.getStudentAnswers().size()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Some Questions Are Not Answered,Please answer all questions.");
        }
        if (quizService.isStudentPrevSubmit(quizSubmission.getStudentID(), quizId)) {
            return ResponseEntity.status(HttpStatus.FOUND).body("You have already submitted.");
        }

        if (quiz.getDeadline().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Late Submission");
        }


        List<QuizSubmission.Answer> correctAnswers = new ArrayList<>();
        List<Double> questionMarks = new ArrayList<>();
        double fullMark = 0;
        for (Question question : quiz.getQuizQuestions()) {
            if (question instanceof MCQ mcq) {
                correctAnswers.add(new QuizSubmission.Answer(mcq.getCorrectAnswer()));
            } else if (question instanceof Essay essay) {
                correctAnswers.add(new QuizSubmission.Answer(essay.getCorrectAnswer()));
            } else if (question instanceof TrueOrFalse trueOrFalse) {
                correctAnswers.add(new QuizSubmission.Answer(trueOrFalse.getCorrectAnswer()));
            }
            fullMark += question.getQuestionMark();
            questionMarks.add(question.getQuestionMark());
        }
        //////////////////////////////////////////////////////////

        double studentMark = 0;
        StringBuilder result = new StringBuilder();
        StringBuilder mark = new StringBuilder();


        List<QuizSubmission.Answer> studentAnswers = new ArrayList<>();
        studentAnswers = quizSubmission.getStudentAnswers();
        for (int i = 0; i < studentAnswers.size(); i++) {
            boolean isCorrect = false;

            if (studentAnswers.get(i).isString() && correctAnswers.get(i).isString()) {
                isCorrect = studentAnswers.get(i).getStringAnswer().equals(correctAnswers.get(i).getStringAnswer());
            } else if (studentAnswers.get(i).isBoolean() && correctAnswers.get(i).isBoolean()) {
                isCorrect = studentAnswers.get(i).getBooleanAnswer().equals(correctAnswers.get(i).getBooleanAnswer());
            }

            if (isCorrect) {
                studentMark += questionMarks.get(i);
            }
            /////////////////////////////////////////////////////
            result.append("Question ").append(i + 1).append('\n');
            result.append("Student Answer: ")
                    .append(studentAnswers.get(i).isString() ? studentAnswers.get(i).getStringAnswer() : studentAnswers.get(i).getBooleanAnswer())
                    .append('\n');
            result.append("Correct Answer: ")
                    .append(correctAnswers.get(i).isString() ? correctAnswers.get(i).getStringAnswer() : correctAnswers.get(i).getBooleanAnswer())
                    .append('\n');

        }
        ///////////////////////////////////////////////////////////////////////////
        // 1/5    // 10/20
        //  /20    //   /10
        // = mark x 20 / fullMark
        double finalMark = (studentMark*quiz.getAssessmentGrade())/fullMark;
        quizSubmission.setGrade(finalMark);

        mark.append("Student Mark: ").append(finalMark).append("/").append(quiz.getAssessmentGrade()).append('\n');
        mark.append(result);

        quizSubmission.setAssessmentId(quizId);
        quizSubmission.setGraded(true);
        quizSubmission.setSubmissionDate(LocalDateTime.now());
        quizService.saveSubmission(quizSubmission);


        return ResponseEntity.ok(mark.toString());
    }
}
