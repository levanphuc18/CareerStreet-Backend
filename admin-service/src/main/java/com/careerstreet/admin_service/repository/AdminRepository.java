package com.careerstreet.admin_service.repository;

import com.careerstreet.admin_service.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository <Admin, Long> {
    Admin findAdminByUsername(String username);
}
