package com.careerstreet.tech_service.repository;

import com.careerstreet.tech_service.entity.Tech;
import com.careerstreet.tech_service.entity.TechDetail;
import com.careerstreet.tech_service.entity.TechDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TechDetailRepository extends JpaRepository<TechDetail, TechDetailId> {

    @Query("select td.tech from TechDetail td where td.id.jobId = :jobId")
    List<Tech> findTechsByJobId(@Param("jobId") Long jobId);
}
