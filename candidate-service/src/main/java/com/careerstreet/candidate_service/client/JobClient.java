package com.careerstreet.candidate_service.client;
import com.careerstreet.candidate_service.dto.ApiResponse;
import com.careerstreet.candidate_service.dto.LevelResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(name = "job-service", url = "http://localhost:9006/api/level")
public interface JobClient {
    @GetMapping("get-level/{levelId}")
    ResponseEntity<ApiResponse<LevelResponse>> getLevelById(@PathVariable Long levelId);
}