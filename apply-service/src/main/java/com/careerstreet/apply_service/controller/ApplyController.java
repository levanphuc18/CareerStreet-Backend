package com.careerstreet.apply_service.controller;

import com.careerstreet.apply_service.dto.ApiResponse;
import com.careerstreet.apply_service.dto.ApplyRequest;
import com.careerstreet.apply_service.dto.ApplyResponse;
import com.careerstreet.apply_service.entity.Apply;
import com.careerstreet.apply_service.exception.GlobalCode;
//import com.careerstreet.apply_service.kafka.producer.ApplyKafkaProducer;
import com.careerstreet.apply_service.service.ApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/apply/")
public class ApplyController {
    private final ApplyService applyService;
    ApplyResponse applyResponse = new ApplyResponse();

    @PostMapping("create")
    public ResponseEntity<ApiResponse<ApplyResponse>> createApply(@RequestBody ApplyRequest applyRequest){
        System.out.println("CB tao apply");
        ApplyResponse applyResponse = applyService.createApply(applyRequest);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS, "tao apply thanh cong", applyResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping("update/{id}/applystatus/{status}")
    public ResponseEntity<ApiResponse<ApplyResponse>> updateApplyStatus(@PathVariable Long id, @PathVariable int status ){
        ApplyResponse applyResponse = applyService.updateApplyStatus(id,status);
        applyResponse.setCandidateCvId(applyResponse.getCandidateCvId());
        applyResponse.setJobId(applyResponse.getJobId());
        applyResponse.setDate(applyResponse.getDate());
        applyResponse.setStatus(applyResponse.getStatus());

        System.out.println("update status apply "+ applyResponse);
        ApiResponse apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "cap nhat trang thai thanh cong", applyResponse);

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("getJobByStatus/{status}")
    public ResponseEntity<ApiResponse<List<ApplyResponse>>> getJobByStatus(@PathVariable int status) {
        // Log khi lấy thông tin CV
        System.out.println("Lấy tat ca apply thuoc status: " + status);

        // Gọi service để lấy job
        List<ApplyResponse> list = applyService.getListAppliesByStatus(status);

        // Tạo response với thông báo thành công
        ApiResponse apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "Danh sach apply thuoc status", list);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("getAppliesByCandidateId/{candidateId}")
    public ResponseEntity<ApiResponse<List<ApplyResponse>>> getAppliesByCandidateId(@PathVariable Long candidateId) {
        // Log khi lấy thông tin CV
        System.out.println("Lấy tất cả apply thuộc candidateId: " + candidateId);

        // Gọi service để lấy danh sách apply
        List<ApplyResponse> list = applyService.getAppliesByCandidateId(candidateId);

        // Tạo ApiResponse với thông báo thành công
        ApiResponse<List<ApplyResponse>> apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "Danh sách apply thuộc candidateId", list);

        // Trả về ApiResponse trong ResponseEntity
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("getAppliesByJobId/{jobId}")
    public ResponseEntity<ApiResponse<List<ApplyResponse>>> getAppliesByJobId(@PathVariable Long jobId) {
        // Log khi lấy thông tin CV
        System.out.println("Lấy tất cả apply thuộc jobId: " + jobId);

        // Gọi service để lấy danh sách apply
        List<ApplyResponse> list = applyService.getAppliesByJobId(jobId);

        // Tạo ApiResponse với thông báo thành công
        ApiResponse<List<ApplyResponse>> apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "Danh sách apply thuộc jobId", list);

        // Trả về ApiResponse trong ResponseEntity
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/check-application")
    public ResponseEntity<?> checkApplicationStatus(@RequestParam Long candidateId, @RequestParam Long jobId) {
        boolean hasApplied = applyService.hasApplyForJob(candidateId, jobId);
        System.out.println("status: "+ hasApplied);
        return ResponseEntity.status(HttpStatus.OK).body(hasApplied);
    }
    @GetMapping("getAppliesByEmployerId/{employerId}")
    public ResponseEntity<ApiResponse<List<ApplyResponse>>> getAppliesByEmployerId(@PathVariable Long employerId) {
        // Log khi lấy thông tin CV
        System.out.println("Lấy tất cả apply thuộc employerId: " + employerId);

        // Gọi service để lấy danh sách apply
        List<ApplyResponse> list = applyService.getListAppliesByEmployer(employerId);

        // Tạo ApiResponse với thông báo thành công
        ApiResponse<List<ApplyResponse>> apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "Danh sách apply thuộc employerId", list);

        // Trả về ApiResponse trong ResponseEntity
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
    @GetMapping("getAppliesByCandidateCv/{candidateCvId}")
    public ResponseEntity<ApiResponse<List<ApplyResponse>>> getAppliesByCandidateCvId(@PathVariable Long candidateCvId){
        System.out.println("Lấy tất cả apply thuộc candidateCvId: " + candidateCvId);
        List<ApplyResponse> list = applyService.getListAppliesByCandidateCv(candidateCvId);
        ApiResponse<List<ApplyResponse>> applyResponse = new ApiResponse<>(GlobalCode.SUCCESS, "Danh sach apply thuộc candidateCvId", list);
        return ResponseEntity.status(HttpStatus.OK).body(applyResponse);
    }
}
