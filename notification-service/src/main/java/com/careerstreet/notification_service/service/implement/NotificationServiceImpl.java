package com.careerstreet.notification_service.service.implement;

import com.careerstreet.notification_service.dto.NotificationRequest;
import com.careerstreet.notification_service.dto.NotificationResponse;
import com.careerstreet.notification_service.entity.Notification;
import com.careerstreet.notification_service.repository.NotificationRepository;
import com.careerstreet.notification_service.service.EmailService;
import com.careerstreet.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final ModelMapper modelMapper;
    private final EmailService emailService;
    @Autowired
    NotificationRepository notificationRepository;

//    public NotificationResponse createNotification(NotificationRequest notificationRequest){
//        Notification notification = new Notification();
//        notification.setSubject(notificationRequest.getSubject());
//        notification.setRecipient(notificationRequest.getRecipient());
//        notification.setMsgBody(notificationRequest.getMsgBody());
//        notification.setStatus("Chưa gửi");
//
//        emailService.sendEmail(notificationRequest);
//        notification.setStatus("Đã gửi");
//
//        notification = notificationRepository.save(notification);
//
//        NotificationResponse notificationResponse = modelMapper.map(notification, NotificationResponse.class);
//
//        return notificationResponse;
//    }

}
