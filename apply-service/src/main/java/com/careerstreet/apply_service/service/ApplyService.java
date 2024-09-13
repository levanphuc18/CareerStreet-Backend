package com.careerstreet.apply_service.service;

import com.careerstreet.apply_service.dto.ApplyRequest;
import com.careerstreet.apply_service.dto.ApplyResponse;

import java.util.List;

public interface ApplyService {
    ApplyResponse createApply(ApplyRequest applyRequest);

    ApplyResponse updateApplyStatus(Long id, int status);

    List<ApplyResponse> getListApplyByStatus(int status);
}
