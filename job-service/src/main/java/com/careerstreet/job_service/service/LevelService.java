package com.careerstreet.job_service.service;

import com.careerstreet.job_service.dto.LevelRequest;
import com.careerstreet.job_service.dto.LevelResponse;

import java.util.List;

public interface LevelService {
    LevelResponse createLevel(LevelRequest levelRequest);
    List<LevelResponse> getAllLevel();
    LevelResponse getLevelById(Long levelId);
}
