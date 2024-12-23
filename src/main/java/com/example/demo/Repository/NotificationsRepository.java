package com.example.demo.Repository;

import com.example.demo.Model.Notifications;
import com.example.demo.Model.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface NotificationsRepository extends JpaRepository<Notifications, String> {
    List<Notifications> findByUser_UserId(String userId);
    List<Notifications> findByUser_UserIdAndRead(String userId, boolean read);

}
