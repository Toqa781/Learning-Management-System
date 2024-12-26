package com.example.demo.Controller;


import com.example.demo.Model.Notifications;
import com.example.demo.Service.NotificationsService;
import com.example.demo.Service.StudentNotificationsService;
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

    @Autowired
    private StudentNotificationsService studentNotificationsService;

//    @PostMapping("/add")
//    public ResponseEntity<Notifications> addNotification(
//            @RequestParam String userId,
//            @RequestParam String message,
//            @RequestParam String userType) {
//        Notifications createdNotification = notificationService.createNotification(userId, message, userType);
//        return ResponseEntity.ok(createdNotification);
//    }

//    @PostMapping("/students")
//    public ResponseEntity<List<Notifications>> sendNotificationToAllStudents(@RequestBody NotificationAllRequest request) {
//        List<Notifications> notifications = notificationService.sendNotificationToAllStudents(
//                request.getContent(),
//                request.getStudentIds()
//        );
//        return ResponseEntity.ok(notifications);
//    }

    @GetMapping("/instructor/{userId}")
    public ResponseEntity<List<Notifications>> getInstructorNotifications(@PathVariable String userId) {
        List<Notifications> notifications = notificationService.getInstructorNotifications(userId);
        return notifications.isEmpty()
                ? ResponseEntity.ok(Collections.emptyList())
                : ResponseEntity.ok(notifications);
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<List<Notifications>> getNotificationsForStudent(@PathVariable("id") String studentId) {
        List<Notifications> notifications = studentNotificationsService.getNotificationsForStudent(studentId);
        return notifications.isEmpty()
                ? ResponseEntity.ok(Collections.emptyList())
                : ResponseEntity.ok(notifications);
    }

}

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