package com.careerstreet.candidate_service.service;

import com.careerstreet.candidate_service.dto.CandidateCvRequest;
import com.careerstreet.candidate_service.dto.CandidateCvResponse;
import com.careerstreet.candidate_service.dto.CandidateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CandidateCvService {
    CandidateCvResponse createCv(CandidateCvRequest candidateCvRequest);

    CandidateCvResponse updateCv(CandidateCvRequest candidateCvRequest, Long candidateCvId);

    CandidateCvResponse getCvById(Long candidateCvId);

    void deleteCv(Long candidateCvId);

    List<CandidateCvResponse> getCvByCandidateId(Long candidateId);

}
