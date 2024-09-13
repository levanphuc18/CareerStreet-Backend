package com.careerstreet.candidate_service.repository;


import com.careerstreet.candidate_service.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository <Candidate, Long> {
    Candidate findCandidateByUsername(String username);

}
