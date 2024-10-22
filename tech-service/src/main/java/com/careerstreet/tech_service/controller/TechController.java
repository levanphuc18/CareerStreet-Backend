package com.careerstreet.tech_service.controller;

import com.careerstreet.tech_service.dto.ApiResponse;
import com.careerstreet.tech_service.dto.TechRequest;
import com.careerstreet.tech_service.dto.TechResponse;
import com.careerstreet.tech_service.exception.GlobalCode;
import com.careerstreet.tech_service.service.TechService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/tech/")
public class TechController {
    private final TechService techService;

    @PostMapping("create")
    public ResponseEntity<ApiResponse<TechResponse>> createTech(@RequestBody TechRequest techRequest){
        System.out.println("CB tao tech");
        TechResponse techResponse = techService.createTech(techRequest);
        ApiResponse apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "tao tech thanh cong", techResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("getall")
    public ResponseEntity<ApiResponse<TechResponse>> getTech(){
        // Log khi lấy thông tin CV
        System.out.println("Lấy tat ca thông tin job ");
        // Gọi service để lấy CV
        List<TechResponse> list = techService.getAllTech();

        // Tạo response với thông báo thành công
        ApiResponse apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "Danh sach tat ca tech", list);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
