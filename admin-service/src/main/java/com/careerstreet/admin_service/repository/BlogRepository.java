package com.careerstreet.admin_service.repository;

import com.careerstreet.admin_service.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository <Blog, Long>{
}
