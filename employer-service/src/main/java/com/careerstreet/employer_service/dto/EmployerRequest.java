package com.careerstreet.employer_service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployerRequest {
    String fullName;
    String address;
    boolean gender;
    LocalDate birthday;
    String phone;
    String avatar;
    String username;
}
