package com.careerstreet.notification_service.service;

import com.careerstreet.event.NotificationEvent;

public interface KafkaConsumerService {
    void listenRegister(NotificationEvent notificationEvent);
    void listenUpdateStatus(NotificationEvent notificationEvent);
}
