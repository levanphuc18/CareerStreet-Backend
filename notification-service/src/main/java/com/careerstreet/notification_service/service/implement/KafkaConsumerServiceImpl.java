package com.careerstreet.notification_service.service.implement;

import com.careerstreet.event.NotificationEvent;
import com.careerstreet.notification_service.dto.NotificationRequest;
import com.careerstreet.notification_service.service.EmailService;
import com.careerstreet.notification_service.service.KafkaConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService {
    @Autowired  // Đảm bảo có annotation này
    EmailService emailService;
    // Phương thức lắng nghe tin nhắn từ topic

    @Override
    @KafkaListener(topics = "notification-register-topic")
    public void listenRegister(NotificationEvent notificationEvent) {
    emailService.sendEmail(notificationEvent);
    System.out.println("Received message register: " + notificationEvent.getRecipient());
    }

//    @Override
    @KafkaListener(topics = "notification-updateStatus-topic")
    public void listenUpdateStatus(NotificationEvent notificationEvent) {
        System.out.println("CB Received message update status kafka: " + notificationEvent.getRecipient());
        emailService.sendEmail(notificationEvent);
        System.out.println("Received message update status kafka: " + notificationEvent.getRecipient());
    }
}
