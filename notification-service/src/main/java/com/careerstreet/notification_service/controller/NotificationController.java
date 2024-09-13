package com.careerstreet.notification_service.controller;

import com.careerstreet.notification_service.dto.ApplyKafka;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {
    // Endpoint cho WebSocket để nhận và gửi tin nhắn
    @MessageMapping("/apply-updates")
    @SendTo("/topic/apply-updates")
    public ApplyKafka sendApplyUpdate(ApplyKafka applyKafka) {
        return applyKafka;
    }
}
