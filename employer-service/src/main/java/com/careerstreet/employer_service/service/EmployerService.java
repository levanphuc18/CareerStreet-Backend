package com.careerstreet.employer_service.service;

import com.careerstreet.employer_service.dto.EmployerRequest;
import com.careerstreet.employer_service.dto.EmployerResponse;

import java.util.List;

public interface EmployerService {

    EmployerResponse createEmployer(EmployerRequest employerRequest);
    EmployerResponse updateEmployerById(EmployerRequest employerRequest, Long employerId);

    EmployerResponse getEmployerByUserName(String username);
    List<EmployerResponse> getAllEmployer();
    Long getEployerIdByUsername(String username);

    EmployerResponse getEmployerById(Long employerId);
}
