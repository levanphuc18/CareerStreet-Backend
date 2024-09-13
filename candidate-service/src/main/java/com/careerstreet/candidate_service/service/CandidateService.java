package com.careerstreet.candidate_service.service;


import com.careerstreet.candidate_service.dto.CandidateRequest;
import com.careerstreet.candidate_service.dto.CandidateResponse;

import java.util.List;

public interface CandidateService {
    CandidateResponse createCandidate(CandidateRequest candidateRequest);
    CandidateResponse updateCandidateById(CandidateRequest candidateRequest, Long candidateId);

    CandidateResponse getCandidateByUserName(String uername);
    List<CandidateResponse> getAllCandidate();

    Long getCandidateIdByUsername(String username);

}
