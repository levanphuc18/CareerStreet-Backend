package com.careerstreet.apply_service.repository;

import com.careerstreet.apply_service.dto.ApplyResponse;
import com.careerstreet.apply_service.entity.Apply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplyRepository extends JpaRepository<Apply, Long> {
    // Tìm tất cả Apply dựa trên candidateCvId
    List<Apply> findByCandidateCvId(Long candidateCvId);
    List<Apply> findAllByJobId(Long jobId);
    Optional<Apply> findFirstByCandidateCvIdAndJobId(Long candidateCvId, Long jobId);

    List<ApplyResponse> findByJobId(Long jobId);
}
