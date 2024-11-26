package com.careerstreet.notification_service.service;


import com.careerstreet.event.NotificationEvent;
import com.careerstreet.notification_service.dto.NotificationRequest;

import java.util.concurrent.CompletableFuture;

public interface EmailService {
    CompletableFuture<String> sendEmailAsync(NotificationEvent notificationEvent);
    String sendEmail(NotificationEvent notificationEvent);
}
