package com.careerstreet.user_service.client;

import com.careerstreet.user_service.dto.AdminResponse;
import com.careerstreet.user_service.dto.ApiResponse;
import com.careerstreet.user_service.dto.CandidateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "admin-service", url = "http://localhost:9003/api/admin")
public interface AdminClient {
    @GetMapping("getbyusername/{username}")
    public ResponseEntity<ApiResponse<AdminResponse>> getAdmin(@PathVariable String username);
}
