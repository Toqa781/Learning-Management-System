package com.example.demo.Service;

import com.example.demo.Model.*;
import com.example.demo.Model.Assessments.Assignment;
import com.example.demo.Model.Assessments.Questions.QuestionBank;
import com.example.demo.Model.Users.Admin;
import com.example.demo.Model.Users.Instructor;
import com.example.demo.Model.Users.Student;
import com.example.demo.Repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // private final Map<String, Course> courses = new HashMap<>();

    public void createCourse(String courseId, String courseName, String courseDescription) {
        Course course = new Course(courseId, courseName, courseDescription);
        courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courseRepository.findAll());
    }

    public Course getCourseById(String courseId) {
        return  courseRepository.findById(courseId).orElse(null);
    }

    public void addLessonToCourse(String courseId, Lesson lesson) {
        Course course = getCourseById(courseId); // Ensures course exists
        course.addLesson(lesson);
        courseRepository.save(course);
    }

    public void enrollCourse(String courseId, Student student) {
        Course course = getCourseById(courseId); // Ensures course exists
        course.enrollStudent(student);
        courseRepository.save(course);
    }

    public List<Student> getEnrolledStudents(String courseId) {
        Course course = getCourseById(courseId); // Ensures course exists
        return course.getEnrolledStudents();
    }

    public void attendLesson(String courseId, Student student, String lessonId, String otp) {
        Course course = getCourseById(courseId); // Ensures course exists
        Lesson lesson = course.getLessons().stream()
                .filter(l -> l.getLessonId().equals(lessonId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found"));

        if (lesson.validateOtp(otp)) {
            student.attendLesson(lessonId);
            courseRepository.save(course);
        } else {
            throw new IllegalArgumentException("Invalid OTP");
        }
    }

//    public void assignQuestionBankToCourse(String courseId , QuestionBank questionBank){
//        Course course = getCourseById(courseId);
//        course.setQuestionBank(questionBank);
//        courseRepository.save(course);
//    }
//
//    public void addAssignmentToCourse(String courseId , Assignment assignment){
//        Course course = getCourseById(courseId);
//        course.getAssignments().add(assignment);
//        courseRepository.save(course);
//    }
}

