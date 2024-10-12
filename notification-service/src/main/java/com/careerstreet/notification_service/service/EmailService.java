package com.careerstreet.notification_service.service;


import com.careerstreet.notification_service.dto.NotificationRequest;

public interface EmailService {
    String sendEmail(NotificationRequest notificationRequest);
}
