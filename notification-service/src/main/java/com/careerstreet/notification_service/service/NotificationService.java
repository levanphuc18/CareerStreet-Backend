package com.careerstreet.notification_service.service;

import com.careerstreet.notification_service.dto.ApplyKafka;
import com.careerstreet.notification_service.dto.NotificationMessage;

public interface NotificationService {
    void handleNotification(NotificationMessage notificationMessage);
}
