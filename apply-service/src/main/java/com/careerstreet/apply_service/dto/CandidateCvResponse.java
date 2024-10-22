package com.careerstreet.apply_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CandidateCvResponse {
    Long candidateCvId;
    String fullName;
    String address;
    String phone;
    String email;
    String school;
    String language;
    String experience;
    String title;
    String currentSalary;
    String preferenceSalary;
    String level;
    String positionType;
    String workLocation;
    String filePath;
    Long candidate_id;
}
