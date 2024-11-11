package com.careerstreet.job_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class JobResponse {
    private Long jobId;
    private String companyName;
    private Long numberOfEmployees;
    private String companyWebsite;
    private String companyOverview;
    private String title;
    private String jobLocation; // Có thể thay đổi nếu cần
    private Long salary;
    private Long numberOfRecruitment;
    private String jobDescription; // Thay đổi từ description thành jobDescription
    private String jobRequirements; // Thay đổi từ requirement thành jobRequirements
    private String benefits;
    private String educationLevel;
    private String jobRank;
    private String jobType;
    private String gender;
    private String contactPerson;
    private String contactPhone;
    private String contactEmail;
    private String contactAddress;
    private LocalDate postingDate;
    private LocalDate expirationDate;
    private Long views;
    private Long status;
    private Long employerId;
    private List<TechResponse> techs;

    // Thêm thuộc tính khóa ngoại liên kết với bảng Level
    private Long levelId;
    private String levelName;
}
