package com.gatepass.data.repositories;

public interface NotificationRepository {

    void sendNotification(String recipient, String subject, String message);

    void sendAccessCode(String recipient, String code, String passType);
}

