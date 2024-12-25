package com.example.demo.Service.Assessments;

import com.example.demo.Model.Assessments.Questions.Essay;
import com.example.demo.Model.Assessments.Questions.MCQ;
import com.example.demo.Model.Assessments.Questions.TrueOrFalse;
import com.example.demo.Model.Assessments.Submissions.AssignmentSubmission;
import com.example.demo.Model.Assessments.Submissions.QuizSubmission;
import com.example.demo.Model.Course;
import com.example.demo.Model.Assessments.Questions.Question;
import com.example.demo.Model.Assessments.Quiz;
import com.example.demo.Model.Users.Student;
import com.example.demo.Repository.Assesments.Questions.QuestionBankRepository;
import com.example.demo.Repository.Assesments.QuizRepository;
import com.example.demo.Repository.Assesments.Submissions.QuizSubmissionRepository;
import com.example.demo.Service.CourseService;
import com.example.demo.Service.NotificationsService;
import com.example.demo.Service.StudentNotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.min;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizSubmissionRepository quizSubmissionRepository;

    @Autowired
    private QuestionBankRepository questionBankRepository;

    @Autowired
    private NotificationsService notificationsService;
    @Autowired
    private StudentNotificationsService studentnotificationsService;


    public Quiz saveQuiz(Quiz quiz, Course course) {
        quiz.setCourseId(course.getCourseId());
        quiz.setQuizQuestions(createQuizQuestions(quiz.getNumberOfQuestions(), questionBankRepository.findByCourseId(course.getCourseId()).getQuestionList()));
        // Save the quiz
        Quiz savedQuiz = quizRepository.save(quiz);

        // Notify students about the new quiz
        List<Student> enrolledStudents = course.getEnrolledStudents();
        String quizName = quiz.getTitle() != null ? quiz.getTitle() : "a quiz";
        for (Student student : enrolledStudents) {
            String message = "A new quiz '" + quizName + "' has been uploaded for the course: " + course.getCourseName();
            studentnotificationsService.createStudentNotification(student, message, "quiz_uploaded", course);
        }

        return savedQuiz;

    }

    private List<Question> createQuizQuestions(int numberOfQuestions, List<Question> questions) {
        Collections.shuffle(questions);
        int size = min(numberOfQuestions, questions.size());
        return questions.subList(0, size);
    }


    public List<Quiz> getQuizesInCourse(String courseId) {
        return quizRepository.findByCourseId(courseId);
    }

    public Quiz getSpecificQuiz(String courseId, Long quizId) {
        List<Quiz> quizList = getQuizesInCourse(courseId);
        for (Quiz quiz : quizList) {
            if (quiz.getId() == quizId) {
                return quiz;
            }
        }
        return null;
    }

    public List<QuizSubmission> getAllStudentsQuizSubmissions(long assessmentId) {
        return quizSubmissionRepository.findQuizSubmissionByAssessmentId(assessmentId);
    }

    public boolean isStudentPrevSubmit(String studentId, Long quizId) {
        List<QuizSubmission> studentSubmissions = getAllStudentsQuizSubmissions(quizId);
        for (QuizSubmission quizSubmission : studentSubmissions) {
            if (Objects.equals(quizSubmission.getStudentID(), studentId)) {
                return true;
            }
        }
        return false;
    }

    public void saveSubmission(QuizSubmission quizSubmission) {
        quizSubmissionRepository.save(quizSubmission);
    }


}
