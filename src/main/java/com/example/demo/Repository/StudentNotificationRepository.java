package com.example.demo.Repository;

import com.example.demo.Model.Notifications;
import com.example.demo.Model.StudentNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentNotificationRepository extends JpaRepository<StudentNotification, Long> {

    List<StudentNotification> findByRecipientId(Long recipientId);

    List<Notifications> findByUser_UserId(String userId);
    List<Notifications> findByUser_UserIdAndRead(String userId, boolean read);
}
