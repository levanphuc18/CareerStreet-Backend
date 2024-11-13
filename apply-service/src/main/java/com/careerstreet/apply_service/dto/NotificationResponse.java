package com.careerstreet.apply_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {
    String subject;
    String recipient;
    String msgBody;
    String status;
}
