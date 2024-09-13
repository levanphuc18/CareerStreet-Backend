package com.careerstreet.apply_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplyResponse {
    Long applyId;
    String candidateCvId;
    String jobId;
    LocalDate date;
    int status;
}
