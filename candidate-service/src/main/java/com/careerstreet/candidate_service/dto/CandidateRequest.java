package com.careerstreet.candidate_service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CandidateRequest {
    String fullName;
    String address;
    boolean gender;
    LocalDate birthday;
    String phone;
    String avatar;
    String username;
}
