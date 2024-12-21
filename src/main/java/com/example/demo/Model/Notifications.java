package com.example.demo.Model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String notificationId;
    private String typeOfNotification; // Type of notification (e.g., "quiz", "assignment")
    private String message;
    private boolean read;
    private String userType;
    private String userId;
    private String timestamp;

    // Constructors
    public Notifications() {
        // Default constructor for JPA
    }

    public Notifications(Long id, String typeOfNotification, String message, boolean read, String userType, String userId, String timestamp) {
        this.id = id;
        this.typeOfNotification = typeOfNotification;
        this.message = message;
        this.read = read;
        this.userType = userType;
        this.userId = userId;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }
    public String getTypeOfNotification() {
        return typeOfNotification;
    }

    public void setTypeOfNotification(String typeOfNotification) {
        this.typeOfNotification = typeOfNotification;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", typeOfNotification='" + typeOfNotification + '\'' +
                ", message='" + message + '\'' +
                ", read=" + read +
                ", userType='" + userType + '\'' +
                ", userId='" + userId + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }


}