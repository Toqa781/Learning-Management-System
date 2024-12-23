package com.example.demo.Model;

import jakarta.persistence.*;


@Entity
public class StudentNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private Long recipientId;

    // Constructors
    public StudentNotification() {
    }

    public StudentNotification(String content, Long recipientId) {
        this.content = content;
        this.recipientId = recipientId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }
}
