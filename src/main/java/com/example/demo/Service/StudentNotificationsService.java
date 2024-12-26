package com.example.demo.Service;


import com.example.demo.Model.Course;
import com.example.demo.Model.Lesson;
import com.example.demo.Model.Notifications;
import com.example.demo.Model.StudentNotification;
import com.example.demo.Model.Users.Student;
import com.example.demo.Repository.NotificationsRepository;
import com.example.demo.Repository.StudentNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentNotificationsService{

    @Autowired
    private NotificationsRepository notificationRepository;

    @Autowired
    private StudentNotificationRepository studentNotificationRepository;

    public StudentNotificationsService(StudentNotificationRepository studentNotificationRepository) {
        this.studentNotificationRepository = studentNotificationRepository;
    }

    // Create a new notification for a student
    @Transactional
    public Notifications createStudentNotification(Student student, String message, String type, Course course) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }
        if (type == null || type.isEmpty()) {
            type = "default"; // Default type if missing
        }

        try {
            StudentNotification notification = new StudentNotification();
            notification.setUser(student);
            notification.setMessage(message);
            notification.setTypeOfNotification(type);
            notification.setCourse(course);
            return studentNotificationRepository.save(notification);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create notification", e);
        }
    }

    // Notify a single student about a new lesson
    @Transactional
    public void notifyStudentOnNewLesson(Student student, Lesson lesson, Course course) {
        String message = "A new lesson '" + lesson.getLessonName() + "' has been added to your course: " + course.getCourseName();
        String type = "lesson_upload";
        createStudentNotification(student, message, type, course);
    }

    // Notify all enrolled students about a new lesson
    @Transactional
    public void notifyAllStudentsOnNewLesson(List<Student> students, Lesson lesson, Course course) {
        String message = "A new lesson '" + lesson.getLessonName() + "' has been added to your course: " + course.getCourseName();
        String type = "lesson_upload";

        for (Student student : students) {
            createStudentNotification(student, message, type, course);
        }
    }

    // Retrieve notifications for a specific student
    public List<Notifications> getNotificationsForStudent(String studentId) {
        try {
            return studentNotificationRepository.findByUser_UserId(studentId);
        } catch (Exception e) {
            return new ArrayList<>(); // Return an empty list in case of any errors
        }
    }

    // Retrieve unread notifications for a specific student
    public List<Notifications> getUnreadNotificationsForStudent(String studentId) {
        return notificationRepository.findByUser_UserIdAndRead(studentId, false);
    }
}
