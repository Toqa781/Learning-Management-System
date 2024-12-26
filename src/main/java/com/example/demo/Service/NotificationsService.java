package com.example.demo.Service;

import com.example.demo.Model.Assessments.Assignment;
import com.example.demo.Model.Assessments.Quiz;
import com.example.demo.Model.Course;
import com.example.demo.Model.Lesson;
import com.example.demo.Model.Notifications;
import com.example.demo.Model.Users.User;
import com.example.demo.Repository.NotificationsRepository;
import jakarta.persistence.EntityNotFoundException;
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


    // Retrieve notifications for instructors
    public List<Notifications> getInstructorNotifications(String userId) {
        return notificationRepository.findByUser_UserId(userId);
    }

    // Retrieve notifications for a specific student
    public List<Notifications> getNotificationsForStudent(Long studentId) {
        return inMemoryNotificationMap.getOrDefault(studentId, new ArrayList<>());
    }

}