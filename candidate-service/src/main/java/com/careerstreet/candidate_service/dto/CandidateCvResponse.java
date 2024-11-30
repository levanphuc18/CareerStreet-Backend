package com.careerstreet.candidate_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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
    Long levelId;
    String levelName;
    String positionType;
    String workLocation;
    String filePath;
    Long candidate_id;
}
