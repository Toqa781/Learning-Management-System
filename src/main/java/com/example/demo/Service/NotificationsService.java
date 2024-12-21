package com.example.demo.Service;

import com.example.demo.Model.Notifications;
import com.example.demo.Repository.NotificationsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class NotificationsService {

    private final NotificationsRepository notificationRepository;

    public NotificationsService(NotificationsRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    // Create new system notifications
    @Transactional
    public Notifications createNotification(String userId, String message, String type) {
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }
        if (type == null || type.isEmpty()) {
            type = "default";  // Set to "default" if type is missing
        }

        try {
            Notifications notification = new Notifications();
            notification.setUserId(userId);
            notification.setMessage(message);
            notification.setTypeOfNotification(type);
            notification.setRead(false); // Default to unread when created
            return notificationRepository.save(notification);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create notification", e);
        }
    }

    // Get all notifications for a user
    public List<Notifications> getNotificationsByUserId(String userId) {
        return notificationRepository.findByUserId(userId);
    }

    // Get only unread notifications for a user
    public List<Notifications> getUnreadNotificationsByUserId(String userId) {
        return notificationRepository.findByUserIdAndRead(userId, false);
    }

    // Get notifications for instructors (students enrolling in their courses)
    public List<Notifications> getInstructorNotifications(String userId) {
        // Example of fetching instructor notifications: students enrolling in their courses
        return notificationRepository.findByUserId(userId); // Assuming notification type 'enrollment'
    }

    // Mark a notification as read
    public Notifications markAsRead(String notificationId) {
        Notifications notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new EntityNotFoundException("Notification with id " + notificationId + " not found"));
        notification.setRead(true);
        return notificationRepository.save(notification);
    }

}
