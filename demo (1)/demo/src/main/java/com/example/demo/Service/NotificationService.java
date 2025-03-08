package com.example.demo.Service;

import com.example.demo.Entity.Notification;
import com.example.demo.Entity.User;
import java.util.List;

public interface NotificationService {
    void sendNotification(User recipient, String message);
    List<Notification> getUserNotifications(Long userId);
}
