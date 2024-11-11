package com.careerstreet.user_service.client;

import com.careerstreet.user_service.dto.ApiResponse;
import com.careerstreet.user_service.dto.NotificationRequest;
import com.careerstreet.user_service.dto.NotificationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service", url = "http://localhost:9001/api/notification")
public interface NotificationClient {
    @PostMapping("create")
    public ResponseEntity<ApiResponse<NotificationResponse>> createNotification(@RequestBody NotificationRequest notificationRequest);
}
