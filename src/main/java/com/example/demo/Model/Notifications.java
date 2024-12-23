package com.example.demo.Model;


import com.example.demo.Model.Users.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "notification_id", nullable = false, unique = true)
    private String notificationId;

    @Column(name = "type_of_notification")
    private String typeOfNotification;

    @Column(name = "message")
    private String message;

    @Column(name = "is_read")
    @JsonIgnore
    private boolean read;

    @Column(name = "user_type")
    private String userType;

    @Column(name = "timestamp")
    private String timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private Course course;

    // Constructors
    public Notifications() {
        // Default constructor for JPA
    }

    public Notifications(Long id, String typeOfNotification, String message, boolean read, String userType, String userId, String timestamp , User user,Course course) {
        this.id = id;
        this.typeOfNotification = typeOfNotification;
        this.message = message;
        this.read = read;
        this.userType = userType;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        this.userType = user.getRole();  // Dynamically set userType based on User's role
    }
    @PrePersist
    protected void onCreate() {
        if (this.timestamp == null) {
            this.timestamp = LocalDateTime.now().toString();
        }
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    // Custom getter to return only course name instead of the whole course object
    public String getCourseName() {
        return this.course != null ? this.course.getCourseName() : null;
    }


    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", typeOfNotification='" + typeOfNotification + '\'' +
                ", message='" + message + '\'' +
                ", read=" + read +
                ", userType='" + userType + '\'' +
                ", userId='" + user + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }


}