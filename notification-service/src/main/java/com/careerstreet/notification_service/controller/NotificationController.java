package com.careerstreet.notification_service.controller;

import com.careerstreet.event.NotificationEvent;
import com.careerstreet.notification_service.service.EmailService;
import com.careerstreet.notification_service.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/notification/")
public class NotificationController {
    @Autowired
    private final KafkaProducerService kafkaProducerService;
    private final EmailService emailService;

    @PostMapping("/send-dto")
    public String sendMessageDTO(@RequestBody NotificationEvent notificationEvent) {
        notificationEvent.setRecipient("rongvua1u8@gmail.com");
        notificationEvent.setSubject("kafka");
        notificationEvent.setMsgBody("test kafka");
//        kafkaProducerService.sendMessageDTO(notificationEvent);
        emailService.sendEmail(notificationEvent);

        return "Message sent: " + notificationEvent.getRecipient();
    }
    @PostMapping("/send-dto-async")
    public String sendMessageDTOAsync(@RequestBody NotificationEvent notificationEvent) {
        notificationEvent.setRecipient("rongvua1u8@gmail.com");
        notificationEvent.setSubject("kafka async");
        notificationEvent.setMsgBody("test kafka");
//        kafkaProducerService.sendMessageDTO(notificationEvent);
        emailService.sendEmailAsync(notificationEvent);

        return "Message sent: " + notificationEvent.getRecipient();
    }
}
