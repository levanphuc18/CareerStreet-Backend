package com.careerstreet.tech_service.service;

import com.careerstreet.tech_service.dto.TechDetailRequest;
import com.careerstreet.tech_service.dto.TechDetailResponse;
import com.careerstreet.tech_service.dto.TechResponse;
import com.careerstreet.tech_service.entity.Tech;

import java.util.List;

public interface TechDetailService {
    void saveTechDetailsForJob(TechDetailRequest techDetailRequest);
    List<TechResponse> getTechByJobId(Long jobId);
}
