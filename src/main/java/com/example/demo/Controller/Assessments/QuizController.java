package com.example.demo.Controller.Assessments;

import com.example.demo.Model.Assessments.Questions.Essay;
import com.example.demo.Model.Assessments.Questions.MCQ;
import com.example.demo.Model.Assessments.Questions.Question;
import com.example.demo.Model.Assessments.Questions.TrueOrFalse;
import com.example.demo.Model.Assessments.Submissions.QuizSubmission;
import com.example.demo.Model.Course;
import com.example.demo.Model.Assessments.Quiz;
import com.example.demo.Model.Users.Student;
import com.example.demo.Service.CourseService;
import com.example.demo.Service.Assessments.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        if (course == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Quiz createdQuiz = quizService.saveQuiz(quiz, course);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuiz);
    }

    @GetMapping("/{quizId}/getQuiz")
    public ResponseEntity<?> getQuiz(@PathVariable String courseId, @PathVariable Long quizId) {
        Quiz quiz = quizService.getSpecificQuiz(courseId, quizId);
        if (quiz != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(quiz);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No quiz found with this ID.");

    }
    @GetMapping("/getQuizes")
    public ResponseEntity<?> getQuiz(@PathVariable String courseId) {
        List<Quiz> quizList = quizService.getQuizesInCourse(courseId);
        if (!quizList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(quizList);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No quizes in course.");

    }

    @PostMapping("/{quizId}/submitQuiz")
    public ResponseEntity<?> submitQuiz(@PathVariable String courseId, @PathVariable Long quizId ,@RequestBody QuizSubmission quizSubmission) {
        Quiz quiz = quizService.getSpecificQuiz(courseId, quizId);
        if (quiz == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No quiz found with this ID.");

        }
        if (quiz.getQuizQuestions().size() != quizSubmission.getStudentAnswers().size()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Some Questions Are Not Answered,Please answer all questions.");
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

        mark.append("Student Mark: ").append(studentMark).append("/").append(fullMark).append('\n');
        mark.append(result);

        quizService.saveSubmission(quizSubmission);

        return ResponseEntity.ok(mark.toString());
    }
}
