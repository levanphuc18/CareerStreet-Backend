package com.careerstreet.apply_service.client;

import com.careerstreet.apply_service.dto.ApiResponse;
import com.careerstreet.apply_service.dto.ApplyResponse;
import com.careerstreet.apply_service.dto.CandidateCvResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "candidate-service", url = "http://localhost:9005/api/candidate-cv")
public interface CandidateCvClient {
    @GetMapping("by-candidate/{candidateId}")
    public ApiResponse<List<CandidateCvResponse>> getCandidateCvBycandidateId(@PathVariable Long candidateId);

    @GetMapping("get/{id}")
    public ResponseEntity<ApiResponse<CandidateCvResponse>> getCvById(@PathVariable Long id);
}
