package com.careerstreet.job_service.client;

import com.careerstreet.job_service.dto.EmployerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "employer-service", url = "http://localhost:9004/api/employer")
public interface EmployerClient {
    @GetMapping("getemployer/{employerId}")
    public ResponseEntity<EmployerResponse> getEmployerById(@PathVariable Long employerId);
}
