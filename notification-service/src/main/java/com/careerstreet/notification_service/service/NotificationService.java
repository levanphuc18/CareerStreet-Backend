package com.careerstreet.notification_service.service;

import com.careerstreet.notification_service.dto.NotificationRequest;
import com.careerstreet.notification_service.dto.NotificationResponse;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService {
    NotificationResponse createNotification(NotificationRequest notificationRequest);
}
