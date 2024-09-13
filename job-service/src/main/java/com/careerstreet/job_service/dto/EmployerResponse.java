package com.careerstreet.job_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class EmployerResponse {
    Long employerId;
    String fullName;
    String address;
    boolean gender;
    String phone;
    String email;
    String avatar;
    String username;
    String company;
    String tax;
}
