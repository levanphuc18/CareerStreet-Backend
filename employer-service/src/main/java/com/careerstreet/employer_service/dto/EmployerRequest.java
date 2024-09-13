package com.careerstreet.employer_service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployerRequest {
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
