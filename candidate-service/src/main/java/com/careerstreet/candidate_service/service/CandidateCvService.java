package com.careerstreet.candidate_service.service;

import com.careerstreet.candidate_service.dto.CandidateCvRequest;
import com.careerstreet.candidate_service.dto.CandidateCvResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CandidateCvService {
    CandidateCvResponse createCv(CandidateCvRequest candidateCvRequest, MultipartFile file);

    CandidateCvResponse updateCv(CandidateCvRequest candidateCvRequest, MultipartFile file, Long candidateCvId);

    CandidateCvResponse getCvById(Long candidateCvId);

    void deleteCv(Long candidateCvId);

    List<CandidateCvResponse> getCvByCandidateId(Long candidateId);
}
