package com.careerstreet.notification_service.kafka.consumer;

import com.careerstreet.notification_service.dto.ApplyKafka;
import com.careerstreet.notification_service.payload.KafkaPayloadProcessor;
//import com.careerstreet.notification_service.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
public class NotificationKafkaConsumer {
//    private final WebSocketService webSocketService;
    private final KafkaPayloadProcessor kafkaPayloadProcessor;

    @KafkaListener(topics = "${spring.kafka.topic.apply-update-status}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenOrderUpdateStatus(@Payload byte[] message) {
        ApplyKafka applyKafka = kafkaPayloadProcessor.deserializePayload(message, ApplyKafka.class);
        System.out.println("Converted OrderKafka: " + applyKafka);
        // Gửi thông báo qua WebSocket
//        webSocketService.sendApplyUpdate(applyKafka);
        // Xử lý thông báo cho đơn hàng
        // notificationService.handleNotification(new NotificationResponse("Order status updated", "Success"));
    }
}
