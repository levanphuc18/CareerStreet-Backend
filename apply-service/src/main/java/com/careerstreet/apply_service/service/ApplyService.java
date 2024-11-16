package com.careerstreet.apply_service.service;

import com.careerstreet.apply_service.dto.ApplyRequest;
import com.careerstreet.apply_service.dto.ApplyResponse;
import com.careerstreet.apply_service.entity.Apply;

import java.util.List;

public interface ApplyService {
    ApplyResponse createApply(ApplyRequest applyRequest);

    ApplyResponse updateApplyStatus(Long id, int status);

    List<ApplyResponse> getListApplyByStatus(int status);

    List<Apply> getAppliesByCandidateId(Long candidateId);
    List<Apply> getAppliesByJobId(Long jobId);
    boolean hasApplyForJob(Long candidateId, Long jobId);
}
