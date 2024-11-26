package com.careerstreet.candidate_service.client;

import com.careerstreet.candidate_service.dto.ApiResponse;
import com.careerstreet.candidate_service.dto.ApplyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "apply-service", url = "http://localhost:9008/api/apply")
public interface ApplyClient {
    @GetMapping("getAppliesByCandidateCv/{candidateCvId}")
    public ResponseEntity<ApiResponse<List<ApplyResponse>>> getAppliesByCandidateCvId(@PathVariable Long candidateCvId);
}
