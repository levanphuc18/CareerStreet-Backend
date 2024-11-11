package com.careerstreet.user_service.client;

import com.careerstreet.user_service.dto.ApiResponse;
import com.careerstreet.user_service.dto.EmployerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "employer-service", url = "http://localhost:9004/api/employer")
public interface EmployerClient {
    @GetMapping("getbyusername/{username}")
    public ResponseEntity<ApiResponse<EmployerResponse>> getEmployer(@PathVariable String username);
}
