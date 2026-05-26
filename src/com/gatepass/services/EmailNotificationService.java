package com.gatepass.services;

import com.gatepass.data.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService implements NotificationRepository {

    @Override
    public void sendNotification(String recipient, String subject, String message) {
        System.out.println("EMAIL to " + recipient + " | Subject: " + subject + " | " + message);
    }

    @Override
    public void sendAccessCode(String recipient, String code, String passType) {
        String subject = "Your Gate Access Code";
        String message = "Your " + passType + " access code is: " + code;
        sendNotification(recipient, subject, message);
    }
}

