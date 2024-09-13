package com.careerstreet.apply_service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ApplyRequest {
    String candidateCvId;
    String jobId;
    LocalDate date;
    int status;
}
