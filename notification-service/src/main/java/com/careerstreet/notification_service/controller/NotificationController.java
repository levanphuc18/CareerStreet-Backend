package com.careerstreet.notification_service.controller;

import com.careerstreet.notification_service.dto.ApiResponse;
import com.careerstreet.notification_service.dto.NotificationRequest;
import com.careerstreet.notification_service.dto.NotificationResponse;
import com.careerstreet.notification_service.entity.Notification;
import com.careerstreet.notification_service.exception.GlobalCode;
import com.careerstreet.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/notification/")
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("create")
    public ResponseEntity<ApiResponse<NotificationResponse>> createNotification(@RequestBody NotificationRequest notificationRequest){
        System.out.println("CB gửi tb");
        NotificationResponse notificationResponse = notificationService.createNotification(notificationRequest);
        ApiResponse apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "gửi thông báo thanh cong", notificationResponse);
    return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
