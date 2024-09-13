package com.careerstreet.apply_service.repository;

import com.careerstreet.apply_service.entity.Apply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyRepository extends JpaRepository<Apply, Long> {
}
