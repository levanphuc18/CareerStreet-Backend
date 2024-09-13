package com.careerstreet.candidate_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CandidateResponse {
    Long candidateId;
    String fullName;
    String address;
    boolean gender;
    LocalDate birthday;
    String phone;
    String avatar;
    String username;
}
