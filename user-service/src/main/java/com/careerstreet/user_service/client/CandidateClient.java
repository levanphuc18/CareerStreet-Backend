package com.careerstreet.user_service.client;

import com.careerstreet.user_service.dto.ApiResponse;
import com.careerstreet.user_service.dto.CandidateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "candidate-service", url = "http://localhost:9005/api/candidate")
public interface CandidateClient {
    @GetMapping(value = "getbyusername/{username}")
    ResponseEntity<ApiResponse<CandidateResponse>> getCandidate(@PathVariable String username);
}
