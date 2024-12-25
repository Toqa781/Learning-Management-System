package com.example.demo.Model;

import com.example.demo.Model.Assessments.Assignment;
import com.example.demo.Model.Assessments.Quiz;
import jakarta.persistence.*;


@Entity
public class StudentNotification extends Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private Long recipientId;

    @ManyToOne
    @JoinColumn(name = "quiz_id", referencedColumnName = "id")
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "assignment_id", referencedColumnName = "id")
    private Assignment assignment;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "courseId")
    private Course course;

    // Constructors
    public StudentNotification() {
    }

    public StudentNotification(String content, Long recipientId) {
        this.content = content;
        this.recipientId = recipientId;
    }

    public StudentNotification(String content, Long recipientId, Quiz quiz, Assignment assignment, Course course) {
        this.content = content;
        this.recipientId = recipientId;
        this.quiz = quiz;
        this.assignment = assignment;
        this.course = course;
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

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}

