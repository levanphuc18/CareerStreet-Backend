package com.careerstreet.tech_service.repository;

import com.careerstreet.tech_service.entity.TechDetail;
import com.careerstreet.tech_service.entity.TechDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechDetailRepository extends JpaRepository<TechDetail, TechDetailId> {
}
