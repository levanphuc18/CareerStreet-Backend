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
    private Long candidateCvId;
    private String fullName;
    private String address;
    private String phone;
    private String email;
    private String school;
    private String language;
    private String experience;
    private String title;
    private String currentSalary;
    private String preferenceSalary;
    private String level;
    private String positionType;
    private String workLocation;
    private String filePath;
    private Long candidate_id;
}
