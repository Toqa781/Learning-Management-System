package com.example.demo.Service;

import com.example.demo.Model.Course;
import com.example.demo.Model.Notifications;
import com.example.demo.Model.Users.User;
import com.example.demo.Repository.NotificationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class NotificationsService {

    @Autowired
    private NotificationsRepository notificationRepository;
    private final Map<Long, List<Notifications>> inMemoryNotificationMap = new HashMap<>();
    private Long currentId = 1L;

    public NotificationsService(NotificationsRepository notificationsRepository) {
        this.notificationRepository = notificationsRepository;
    }

    // Create new system notification
    @Transactional
    public Notifications createNotification(User userId, String message, String type, Course course) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }
        if (type == null || type.isEmpty()) {
            type = "default";
        }

        try {
            Notifications notification = new Notifications();
//            notification.setNotificationId(String.valueOf(currentId++));
            notification.setUser(userId);
            notification.setMessage(message);
            notification.setTypeOfNotification(type);
            //notification.setRead(false);
            notification.setCourse(course);

            return notificationRepository.save(notification);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create notification", e);
        }

    }

//    // Send notification to all students
//    public List<Notifications> sendNotificationToAllStudents(String content, List<Long> studentIds) {
//        List<Notifications> sentNotifications = new ArrayList<>();
//
//        for (Long studentId : studentIds) {
//            Notifications notification = new Notifications();
//            notification.setNotificationId(String.valueOf(currentId++));
//            notification.setUserId(String.valueOf(studentId));
//            notification.setMessage(content);
//            notification.setTypeOfNotification("student_notification");
//            notification.setRead(false);
//
//            // Save to database
//            Notifications savedNotification = notificationRepository.save(notification);
//
//            // Maintain in-memory map
//            inMemoryNotificationMap.putIfAbsent(studentId, new ArrayList<>());
//            inMemoryNotificationMap.get(studentId).add(savedNotification);
//
//            sentNotifications.add(savedNotification);
//        }
//
//        return sentNotifications;
//    }

    // Retrieve notifications for a specific user
//    public List<Notifications> getNotificationsByUserId(String userId) {
//        return notificationRepository.findByUser_UserId(userId);
//    }

    //retrieve unread notifications for a specific user
    public List<Notifications> getUnreadNotificationsByUserId(String userId) {
        return notificationRepository.findByUser_UserIdAndRead(userId, false);
    }

    //retrieve notifications for instructors
    public List<Notifications> getInstructorNotifications(String userId) {
        return notificationRepository.findByUser_UserId(userId); // Can add filtering logic for instructor-specific notifications
    }

    //retrieve notifications for a specific student
    public List<Notifications> getNotificationsForStudent(Long studentId) {
        return inMemoryNotificationMap.getOrDefault(studentId, new ArrayList<>());
    }

}