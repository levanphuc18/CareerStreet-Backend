package com.careerstreet.candidate_service.repository;

import com.careerstreet.candidate_service.dto.CandidateCvResponse;
import com.careerstreet.candidate_service.entity.CandidateCv;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateCvRepository extends JpaRepository<CandidateCv, Long> {
    List<CandidateCvResponse> findCandidateCvByCandidate_CandidateId(Long candidateID);
}
