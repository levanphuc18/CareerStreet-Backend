package com.careerstreet.candidate_service.dto;

import com.careerstreet.candidate_service.entity.Candidate;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CandidateCvRequest {
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
    String positionType;
    String workLocation;
    Long candidate_id;
}
