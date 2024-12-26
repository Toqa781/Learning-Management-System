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


    @GetMapping("/instructor/{userId}")
    public ResponseEntity<List<Notifications>> getInstructorNotifications(@PathVariable String userId) {
        List<Notifications> notifications = notificationService.getInstructorNotifications(userId);
        return notifications.isEmpty()
                ? ResponseEntity.ok(Collections.emptyList())
                : ResponseEntity.ok(notifications);
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<List<Notifications>> getNotificationsForStudent(@PathVariable("id") Long studentId) {
        List<Notifications> notifications = notificationService.getNotificationsForStudent(studentId);
        return notifications.isEmpty()
                ? ResponseEntity.ok(Collections.emptyList())
                : ResponseEntity.ok(notifications);
    }

}