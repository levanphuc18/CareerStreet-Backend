package com.careerstreet.candidate_service.dto;

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
    Long candidateCvId;
    Long jobId;
    String coverLetter;
    LocalDate date;
    int status;
}
