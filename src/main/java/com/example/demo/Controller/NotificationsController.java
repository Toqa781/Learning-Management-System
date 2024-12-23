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

    // POST: Add a new notification for a specific user
//    @PostMapping("/add")
//    public ResponseEntity<Notifications> addNotification(
//            @RequestParam String userId,
//            @RequestParam String message,
//            @RequestParam String userType) {
//        Notifications createdNotification = notificationService.createNotification(userId, message, userType);
//        return ResponseEntity.ok(createdNotification);
//    }

//    // POST: Send a notification to all students
//    @PostMapping("/students")
//    public ResponseEntity<List<Notifications>> sendNotificationToAllStudents(@RequestBody NotificationAllRequest request) {
//        List<Notifications> notifications = notificationService.sendNotificationToAllStudents(
//                request.getContent(),
//                request.getStudentIds()
//        );
//        return ResponseEntity.ok(notifications);
//    }

    // GET: Retrieve notifications for a specific user (all or unread only)
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

    // GET: Retrieve notifications for instructors (students enrolling in their courses)
    @GetMapping("/instructor/{userId}")
    public ResponseEntity<List<Notifications>> getInstructorNotifications(@PathVariable String userId) {
        List<Notifications> notifications = notificationService.getInstructorNotifications(userId);
        return notifications.isEmpty()
                ? ResponseEntity.ok(Collections.emptyList())
                : ResponseEntity.ok(notifications);
    }

    // GET: Retrieve notifications for a specific student
    @GetMapping("/student/{id}")
    public ResponseEntity<List<Notifications>> getNotificationsForStudent(@PathVariable("id") Long studentId) {
        List<Notifications> notifications = notificationService.getNotificationsForStudent(studentId);
        return notifications.isEmpty()
                ? ResponseEntity.ok(Collections.emptyList())
                : ResponseEntity.ok(notifications);
    }

    // PUT: Mark a notification as read
    @PutMapping("/{userId}/mark-read/{notificationId}")
    public ResponseEntity<String> markAsRead(@PathVariable String userId, @PathVariable String notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.ok("Notification marked as read for user " + userId);
    }
}

// DTO for sending notifications to all students
class NotificationAllRequest {
    private String content;
    private List<Long> studentIds;

    // Getters and Setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Long> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<Long> studentIds) {
        this.studentIds = studentIds;
    }
}