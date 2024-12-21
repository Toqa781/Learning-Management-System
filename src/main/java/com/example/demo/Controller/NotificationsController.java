package com.example.demo.Controller;


import com.example.demo.Model.Notifications;
import com.example.demo.Service.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationsController {
    @Autowired
    private NotificationsService notificationService;

    // Add a new notification
    @PostMapping("/add")
    public ResponseEntity<Notifications> addNotification(@RequestParam String userId, @RequestParam String message, @RequestParam String userType) {
        Notifications createdNotification = notificationService.createNotification(userId, message, userType);
        return ResponseEntity.ok(createdNotification);
    }


    // Get notifications (all or unread only)
    @GetMapping("/{userId}")
    public ResponseEntity<List<Notifications>> getNotifications(
            @PathVariable String userId,
            @RequestParam(defaultValue = "false") boolean unreadOnly) {
        List<Notifications> notifications;
        if (unreadOnly) {
            notifications = notificationService.getUnreadNotificationsByUserId(userId);
        } else {
            notifications = notificationService.getNotificationsByUserId(userId);
        }
        return notifications.isEmpty()
                ? ResponseEntity.ok(Collections.emptyList())
                : ResponseEntity.ok(notifications);
    }


    // Get notifications for instructors (students enrolling in their courses)
    @GetMapping("/instructor/{userId}")
    public ResponseEntity<List<Notifications>> getInstructorNotifications(@PathVariable String userId) {
        List<Notifications> notifications = notificationService.getInstructorNotifications(userId);
        return notifications.isEmpty()
                ? ResponseEntity.ok(Collections.emptyList())
                : ResponseEntity.ok(notifications);
    }
    // Mark a notification as read
    @PutMapping("/{userId}/mark-read/{notificationId}")
    public ResponseEntity<String> markAsRead(@PathVariable String userId, @PathVariable String notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.ok("Notification marked as read for user " + userId);
    }
}
