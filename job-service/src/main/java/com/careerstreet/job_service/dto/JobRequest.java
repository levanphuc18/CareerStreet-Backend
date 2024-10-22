package com.careerstreet.job_service.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class JobRequest {
    private String companyName;
    private Long numberOfEmployees;
    private String companyWebsite;
    private String companyOverview;
    private String title;
    private String jobLocation; // Có thể thay đổi nếu cần
    private Long salary;
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
    private List<Long> techIds;

    // Thêm thuộc tính khóa ngoại liên kết với bảng Level
    private Long levelId;
}
