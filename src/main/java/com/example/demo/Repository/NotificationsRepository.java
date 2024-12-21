package com.example.demo.Repository;

import com.example.demo.Model.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationsRepository extends JpaRepository<Notifications, String> {
    List<Notifications> findByUserId(String userId);
    List<Notifications> findByUserIdAndRead(String userId, boolean read);
}
