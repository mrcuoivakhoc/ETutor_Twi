package com.example.demo.Repository;

import com.example.demo.Entity.Notification;
import com.example.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByRecipient(User recipient);
}
