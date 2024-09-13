package com.careerstreet.tech_service.repository;

import com.careerstreet.tech_service.entity.Tech;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechRepository extends JpaRepository<Tech, Long> {
}
