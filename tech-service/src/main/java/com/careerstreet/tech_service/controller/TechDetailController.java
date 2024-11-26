package com.careerstreet.tech_service.controller;

import com.careerstreet.tech_service.dto.ApiResponse;
import com.careerstreet.tech_service.dto.TechDetailRequest;
import com.careerstreet.tech_service.dto.TechRequest;
import com.careerstreet.tech_service.dto.TechResponse;
import com.careerstreet.tech_service.entity.Tech;
import com.careerstreet.tech_service.exception.GlobalCode;
import com.careerstreet.tech_service.service.TechDetailService;
import com.careerstreet.tech_service.service.TechService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/tech-detail/")
public class TechDetailController {
    private final TechDetailService techDetailService;

    // API lưu thông tin TechDetails cho một công việc
    @PostMapping("create")
    public ResponseEntity<String> saveTechDetailsForJob(
            @RequestBody TechDetailRequest techDetailRequest) {
        try {
            // Gọi hàm lưu tech details
            techDetailService.saveTechDetailsForJob(techDetailRequest);
            return ResponseEntity.ok("Lưu thông tin công nghệ thành công cho jobId: " + techDetailRequest.getJobId());
        } catch (Exception e) {
            // Xử lý ngoại lệ và trả về lỗi nếu có
            return ResponseEntity.status(500).body("Lỗi khi lưu thông tin công nghệ: " + e.getMessage());
        }
    }

    @GetMapping("/get-tech/{jobId}")
    public ResponseEntity<ApiResponse<List<TechResponse>>> getTechByJobId(@PathVariable Long jobId){
        System.out.println("gget all tech: " + jobId);
        List<TechResponse> list = techDetailService.getTechByJobId(jobId);
        System.out.println(list.size());
        // Tạo response với thông báo thành công
        ApiResponse apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "Danh sach tech cua job", list);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
