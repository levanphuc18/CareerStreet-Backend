//package com.careerstreet.notification_service.service;
//
//import com.careerstreet.notification_service.dto.ApplyKafka;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class WebSocketService {
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;
//
//    public void sendApplyUpdate(ApplyKafka applyKafka) {
//        String destination = "/topic/apply-updates";
//        messagingTemplate.convertAndSend(destination, applyKafka);
//    }
//}
