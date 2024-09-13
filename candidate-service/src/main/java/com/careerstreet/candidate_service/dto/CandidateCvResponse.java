package com.careerstreet.candidate_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CandidateCvResponse {
    private Long candidateCvId;
    private String description;
    private String experience;
    private String link;
    private Long candidate_id;
}
