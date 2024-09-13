package com.careerstreet.tech_service.service;

import com.careerstreet.tech_service.dto.TechRequest;
import com.careerstreet.tech_service.dto.TechResponse;

public interface TechService {
    TechResponse createTech(TechRequest techRequest);
}
