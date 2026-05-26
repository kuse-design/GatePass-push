
package com.gatepass.services;

import com.gatepass.data.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class SmsNotificationService implements NotificationRepository {
    @Override
    public void sendNotification(String recipient, String subject, String message) {
        System.out.println("SMS to " + recipient + " | " + message);
    }

    @Override
    public void sendAccessCode(String recipient, String code, String passType) {
        String message = "Your " + passType + " gate access code is: " + code;
        sendNotification(recipient, null, message);
    }
}
