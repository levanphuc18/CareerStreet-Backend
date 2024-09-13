package com.careerstreet.job_service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class JobRequest {
    String title;
    String description;
    String requirement;
    String salary;
    String address;
    String workType;
    LocalDate postingDate;
    LocalDate deadline;
    Long techDetailId;
    Long employerId;
    Long level_id;
}
