package com.example.demo.Service;

import com.example.demo.Entity.Notification;
import com.example.demo.Entity.User;
import com.example.demo.Repository.NotificationRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void sendNotification(User recipient, String message) {
        Notification notification = new Notification(recipient, message);
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getUserNotifications(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new RuntimeException("User not found!"));
        return notificationRepository.findByRecipient(user);
    }
}
