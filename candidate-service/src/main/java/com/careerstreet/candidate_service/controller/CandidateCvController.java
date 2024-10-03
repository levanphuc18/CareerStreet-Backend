package com.careerstreet.candidate_service.controller;

import com.careerstreet.candidate_service.dto.*;
import com.careerstreet.candidate_service.exception.GlobalCode;
import com.careerstreet.candidate_service.service.CandidateCvService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/candidate-cv/")
public class CandidateCvController {
    private final CandidateCvService candidateCvService;

    @PostMapping("create")
    public ResponseEntity<ApiResponse<CandidateCvResponse>> createCv(
            @ModelAttribute CandidateCvRequest candidateCvRequest) { // Chú ý sử dụng @ModelAttribute

        // In thông tin ra console để kiểm tra
        System.out.println("ID ứng viên: " + candidateCvRequest.getCandidate_id());
        System.out.println("Ngôn ngữ: " + candidateCvRequest.getLanguage());
        System.out.println("Kinh nghiệm: " + candidateCvRequest.getExperience());

        System.out.println("Bắt đầu tạo CV ứng viên...");

        // Tạo CV thông qua service
        CandidateCvResponse candidateCvResponse = candidateCvService.createCv(candidateCvRequest);

        // Tạo phản hồi API
        ApiResponse<CandidateCvResponse> apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "Tạo CV ứng viên thành công", candidateCvResponse);
        System.out.println("Tạo CV ứng viên thành công.");

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }



    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse<CandidateCvResponse>> updateCv (@RequestBody CandidateCvRequest candidateCvRequest, @PathVariable Long id){
        System.out.println(" Cb cap nhat CV ung vien ");
//        candidateCvRequest.setCandidate_id(candidateCvRequest.getCandidate_id());
        CandidateCvResponse candidateCvResponse = candidateCvService.updateCv(candidateCvRequest, id);

        ApiResponse apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "Cap nhat cv ung vien thanh cong", candidateCvResponse);
        System.out.println("Cap nhat cv ung vien thanh cong rsp New");
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCv(@PathVariable Long id) {
        // Log khi xóa CV
        System.out.println("Xóa CV với ID: " + id);

        // Gọi service để xóa CV
        candidateCvService.deleteCv(id);

        // Tạo response với thông báo thành công
        ApiResponse<Void> apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "Xóa CV ứng viên thành công", null);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }


    @GetMapping("get/{id}")
    public ResponseEntity<ApiResponse<CandidateCvResponse>> getCvById(@PathVariable Long id) {
        // Log khi lấy thông tin CV
        System.out.println("Lấy thông tin CV với ID: " + id);

        // Gọi service để lấy CV
        CandidateCvResponse candidateCvResponse = candidateCvService.getCvById(id);

        // Tạo response với thông báo thành công
        ApiResponse<CandidateCvResponse> apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "Lấy thông tin CV thành công", candidateCvResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("by-candidate/{candidateId}")
    public ResponseEntity<ApiResponse<List<CandidateCvResponse>>> getAllCandidateCv(@PathVariable Long candidateId) {
        // Log khi lấy thông tin CV
        System.out.println("Lấy tat ca thông tin CV với ID: " + candidateId);

        // Gọi service để lấy CV
        List<CandidateCvResponse> list = candidateCvService.getCvByCandidateId(candidateId);

        // Tạo response với thông báo thành công
        ApiResponse apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "Danh sach cv", list);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

}
