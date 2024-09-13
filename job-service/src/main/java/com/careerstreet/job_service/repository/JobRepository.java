package com.careerstreet.job_service.repository;

import com.careerstreet.job_service.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long > {
}
