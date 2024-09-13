package com.careerstreet.candidate_service.dto;

import com.careerstreet.candidate_service.entity.Candidate;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class CandidateCvRequest {
    String description;
    String experience;
    String link;
    Long candidate_id;
}
