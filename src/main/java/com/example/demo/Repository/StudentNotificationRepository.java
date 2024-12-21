package com.example.demo.Repository;

import com.example.demo.Model.StudentNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentNotificationRepository extends JpaRepository<StudentNotification, Long> {

    // Custom query to find notifications by student ID (recipientId)
    List<StudentNotification> findByRecipientId(Long recipientId);
}
