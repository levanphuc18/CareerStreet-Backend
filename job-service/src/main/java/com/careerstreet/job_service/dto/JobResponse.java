package com.careerstreet.job_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class JobResponse {
    Long jobId;
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
    String levelName;
    String companyName;
}
