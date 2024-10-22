package com.careerstreet.apply_service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ApplyRequest {
    Long candidateCvId;
    String jobId;
    String coverLetter;
    int status;
}
