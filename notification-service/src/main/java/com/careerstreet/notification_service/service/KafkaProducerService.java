package com.careerstreet.notification_service.service;

import com.careerstreet.notification_service.dto.NotificationRequest;

public interface KafkaProducerService {
    void sendMessageDTO(NotificationRequest notificationRequest);
}
