package com.careerstreet.admin_service.service;

import com.careerstreet.admin_service.dto.AdminRequest;
import com.careerstreet.admin_service.dto.AdminResponse;

public interface AdminService {
    AdminResponse createAdmin(AdminRequest adminRequest);
    AdminResponse getAdminByUserName(String username);
}
