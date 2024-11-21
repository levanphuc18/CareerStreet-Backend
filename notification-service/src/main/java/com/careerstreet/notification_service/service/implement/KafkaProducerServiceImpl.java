package com.careerstreet.notification_service.service.implement;

import com.careerstreet.event.NotificationEvent;
import com.careerstreet.notification_service.dto.NotificationRequest;
import com.careerstreet.notification_service.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplateDTO;

    private static final String TOPICNEW = "email-topic";

    public void sendMessageDTO(NotificationEvent notificationEvent) {
        kafkaTemplateDTO.send(TOPICNEW, notificationEvent);
        System.out.println("Sent message: " + notificationEvent.getRecipient());
    }
}
