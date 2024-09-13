package com.careerstreet.job_service.service;

import com.careerstreet.job_service.dto.LevelRequest;
import com.careerstreet.job_service.dto.LevelResponse;

public interface LevelService {
    LevelResponse createLevel(LevelRequest levelRequest);
}
