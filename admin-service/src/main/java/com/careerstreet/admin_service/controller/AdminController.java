package com.careerstreet.admin_service.controller;

import com.careerstreet.admin_service.dto.AdminRequest;
import com.careerstreet.admin_service.dto.AdminResponse;
import com.careerstreet.admin_service.dto.ApiResponse;
import com.careerstreet.admin_service.exception.GlobalCode;
import com.careerstreet.admin_service.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin/")
public class AdminController {
    private final AdminService adminService;

    @PostMapping("create")
    public ResponseEntity<ApiResponse<AdminResponse>> createAdmin(@RequestBody AdminRequest adminRequest){
        System.out.println("Cbi tao admin");
        AdminResponse adminResponse = adminService.createAdmin(adminRequest);
        ApiResponse apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "Tao admin thanh cong", adminResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
