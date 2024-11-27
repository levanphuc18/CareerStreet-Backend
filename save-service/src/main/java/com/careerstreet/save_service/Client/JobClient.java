package com.careerstreet.save_service.Client;

import com.careerstreet.save_service.dto.ApiResponse;
import com.careerstreet.save_service.dto.JobResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "job-service", url = "http://localhost:9006/api/job/")
public interface JobClient {
   @GetMapping("get/{id}")
   ApiResponse<JobResponse> getJobById(@PathVariable("id") Long id);
} 