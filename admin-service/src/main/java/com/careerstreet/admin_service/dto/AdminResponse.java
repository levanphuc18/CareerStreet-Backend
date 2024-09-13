package com.careerstreet.admin_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AdminResponse {
    Long adminId;
    String fullName;
    String avatar;
    String contact;
    String username;
}
