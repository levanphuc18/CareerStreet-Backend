package com.careerstreet.notification_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplyKafka {
    String uuid;
    Long applyId;
    String candidateCvId;
    String jobId;
    LocalDate date;
    int status;
}
