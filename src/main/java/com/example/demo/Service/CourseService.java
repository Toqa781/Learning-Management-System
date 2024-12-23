package com.example.demo.Service;

import com.example.demo.Model.*;
import com.example.demo.Model.Assessments.Assignment;
import com.example.demo.Model.Assessments.Questions.QuestionBank;
import com.example.demo.Model.Users.Admin;
import com.example.demo.Model.Users.Instructor;
import com.example.demo.Model.Users.Student;
import com.example.demo.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final NotificationsService
    notificationsService;

    public CourseService(CourseRepository courseRepository, NotificationsService notificationsService) {
        this.courseRepository = courseRepository;
        this.notificationsService = notificationsService;
    }

    // private final Map<String, Course> courses = new HashMap<>();

    public void createCourse(String courseId, String courseName, String courseDescription, Instructor creator) {
        Course course = new Course(courseId, courseName, courseDescription, creator);
        courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getCoursesByInstructor(String instructorId) {
        return courseRepository.findByCreator_UserId(instructorId);
    }



    public Course getCourseById(String courseId) {
        return  courseRepository.findById(courseId).orElse(null);
    }

    public void addLessonToCourse(String courseId, Lesson lesson) {
        Course course = getCourseById(courseId); // Ensures course exists
        lesson.setOtp(UUID.randomUUID().toString());
        course.addLesson(lesson);
        courseRepository.save(course);
    }

    public void enrollCourse(String courseId, Student student) {
        Course course = getCourseById(courseId); // Ensures course exists
        course.enrollStudent(student);
        courseRepository.save(course);
        // Notify the instructor
        String message = "Student " + student.getUserId() + " has enrolled in your course: " + course.getCourseName();
        notificationsService.createNotification(course.getCreator(), message, "student_enrollment",course);
    }

    @Transactional
    public List<Student> getEnrolledStudents(String courseId) {
        Course course = getCourseById(courseId); // Ensures course exists
        course.getEnrolledStudents().size();
        return course.getEnrolledStudents();
    }

    @Transactional
    public void attendLesson(String courseId, Student student, String lessonId, String otp) {
        Course course = getCourseById(courseId); // Ensures course exists
        Lesson lesson = course.getLessons().stream()
                .filter(l -> l.getLessonId().equals(lessonId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found"));

        if (lesson.validateOtp(otp)) {
            student.attendLesson(lessonId);
            course.getEnrolledStudents().stream()
                    .filter(enrolledStudent -> enrolledStudent.getUserId().equals(student.getUserId()))
                    .findFirst()
                    .ifPresent(enrolledStudent -> enrolledStudent.attendLesson(lessonId));
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

