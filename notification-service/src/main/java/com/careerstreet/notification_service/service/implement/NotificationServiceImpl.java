package com.careerstreet.notification_service.service.implement;

import com.careerstreet.notification_service.dto.ApplyKafka;
import com.careerstreet.notification_service.dto.NotificationMessage;
import com.careerstreet.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    @Override
    public void handleNotification(NotificationMessage notificationMessage) {
        System.out.println("Received notification: " + notificationMessage.getMessage());
    }
}
