package com.careerstreet.apply_service.client;

import com.careerstreet.apply_service.dto.ApiResponse;
import com.careerstreet.apply_service.dto.JobResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "job-service", url = "http://localhost:9006/api/job")
public interface JobClient {
    @GetMapping("getJobByEmployer/{employerId}")
    public ResponseEntity<ApiResponse<List<JobResponse>>> getAllJobByemployerId(@PathVariable Long employerId);
}
