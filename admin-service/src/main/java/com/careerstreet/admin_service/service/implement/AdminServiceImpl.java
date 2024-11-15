package com.careerstreet.admin_service.service.implement;

import com.careerstreet.admin_service.dto.AdminRequest;
import com.careerstreet.admin_service.dto.AdminResponse;
import com.careerstreet.admin_service.entity.Admin;
import com.careerstreet.admin_service.exception.EntityNotFoundException;
import com.careerstreet.admin_service.exception.GlobalCode;
import com.careerstreet.admin_service.repository.AdminRepository;
import com.careerstreet.admin_service.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final ModelMapper modelMapper;
    private final AdminRepository adminRepository;

    @Override
    public AdminResponse createAdmin(AdminRequest adminRequest){
        Admin admin = modelMapper.map(adminRequest, Admin.class);

        admin = adminRepository.save(admin);

        AdminResponse adminResponse = modelMapper.map(admin, AdminResponse.class);

        return adminResponse;
    }

    @Override
    public AdminResponse getAdminByUserName(String username) {
        Admin admin = adminRepository.findAdminByUsername(username);
        System.out.println("employer ID: "+admin.getAdminId());
        AdminResponse adminResponse = modelMapper.map(admin, AdminResponse.class);
        return adminResponse;
    }
}
