package com.careerstreet.notification_service.service;

import com.careerstreet.event.NotificationEvent;

public interface KafkaConsumerService {
    void listen(String message);
    void listenRegister(NotificationEvent notificationEvent);
    void listenUpdateStatus(NotificationEvent notificationEvent);
}
