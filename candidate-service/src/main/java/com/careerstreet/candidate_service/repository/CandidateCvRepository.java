package com.careerstreet.candidate_service.repository;

import com.careerstreet.candidate_service.dto.CandidateCvResponse;
import com.careerstreet.candidate_service.entity.CandidateCv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CandidateCvRepository extends JpaRepository<CandidateCv, Long> {
    List<CandidateCvResponse> findCandidateCvByCandidate_CandidateId(Long candidateID);
    // Truy vấn để lấy danh sách candidateCvId từ candidateId
    @Query("SELECT cv.candidateCvId FROM CandidateCv cv WHERE cv.candidate.candidateId = :candidateId")
    List<Long> findCandidateCvIdsByCandidateId(Long candidateId);
}
