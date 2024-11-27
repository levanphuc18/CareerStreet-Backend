package com.careerstreet.save_service.repository;

import com.careerstreet.save_service.entity.Save;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaveRepository extends JpaRepository<Save, Long> {
    List<Save> findByCandidateId(Long candidateId);
    void deleteByCandidateIdAndJobId(Long candidateId, Long jobId);
    boolean existsByCandidateIdAndJobId(Long candidateId, Long jobId);
    Optional<Save> findByCandidateIdAndJobId(Long candidateId, Long JobId);
}