package com.careerstreet.job_service.repository;

import com.careerstreet.job_service.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long > {
    List<Job> findAllByStatus(Long status);
    @Modifying
    @Query("UPDATE Job j SET j.views = j.views + 1 WHERE j.jobId = :jobId")
    void updateViews(@Param("jobId") Long jobId);
}
