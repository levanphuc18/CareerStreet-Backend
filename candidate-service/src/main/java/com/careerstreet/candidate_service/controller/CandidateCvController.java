package com.careerstreet.candidate_service.controller;

import com.careerstreet.candidate_service.client.ApplyClient;
import com.careerstreet.candidate_service.dto.*;
import com.careerstreet.candidate_service.exception.GlobalCode;
import com.careerstreet.candidate_service.service.CandidateCvService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/candidate-cv/")
public class CandidateCvController {
    private final CandidateCvService candidateCvService;
    private final ApplyClient applyClient;

    @PostMapping("create")
//    @PostMapping(value = "create", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ApiResponse<CandidateCvResponse>> createCv(
            @RequestPart(name = "file",required = false) MultipartFile file,
            @RequestPart(name = "data") CandidateCvRequest candidateCvRequest) { // Chú ý sử dụng @ModelAttribute
        System.out.println("Thông tin ứng viên: " + candidateCvRequest);

        // Xử lý file nếu cần
        if (file != null) {
            // Xử lý file (ví dụ: lưu vào thư mục, phân tích nội dung, v.v.)
            System.out.println("File được gửi: " + file.getOriginalFilename());
        }

        System.out.println("Bắt đầu tạo CV ứng viên...");

        CandidateCvResponse candidateCvResponse = candidateCvService.createCv(candidateCvRequest,file);

        // Tạo phản hồi API
        ApiResponse<CandidateCvResponse> apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "Tạo hồ sơ ứng viên thành công", candidateCvResponse);
        System.out.println("Tạo hồ sơ ứng viên thành công.");

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse<CandidateCvResponse>> updateCv (
            @RequestPart(name = "file",required = false) MultipartFile file,
            @RequestPart(name = "data") CandidateCvRequest candidateCvRequest,
            @PathVariable Long id){
        System.out.println(" Cb cap nhat CV ung vien ");
        CandidateCvResponse candidateCvResponse = candidateCvService.updateCv(candidateCvRequest,file, id);

        ApiResponse apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "Cap nhat cv ung vien thanh cong", candidateCvResponse);
        System.out.println("Cap nhat cv ung vien thanh cong rsp New");
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCv(@PathVariable Long id) {
        // Log khi xóa CV
        System.out.println("Xóa CV với ID: " + id);

        // Lấy danh sách Apply của CV từ FeignClient
        ApiResponse<List<ApplyResponse>> response = applyClient.getAppliesByCandidateCvId(id).getBody();

        // Lấy danh sách ApplyResponse từ phản hồi
        List<ApplyResponse> applyResponses = response.getData();
        System.out.println("Có " + applyResponses.size() + " ApplyResponse");

        // Kiểm tra xem có Apply nào có status không thể xóa không
        for (ApplyResponse applyResponse : applyResponses) {
            // Nếu trạng thái không phải là -1 hoặc 5 thì không thể xóa
            if (applyResponse.getStatus() != -1 && applyResponse.getStatus() != 5) {
                System.out.println("Không thể xóa xóa vì có Apply không hợp lệ");
                ApiResponse<Void> apiResponse = new ApiResponse<>(GlobalCode.ERROR_ENTITY_NOT_FOUND, "Không thể xóa hồ sơ, hồ sơ đang được ứng tuyển", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
        }

        // Nếu tất cả Apply đều có status hợp lệ, tiến hành xóa CV
        candidateCvService.deleteCv(id);
        ApiResponse<Void> apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "Xóa hồ sơ thành công", null);
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
    public ResponseEntity<ApiResponse<List<CandidateCvResponse>>> getCandidateCvBycandidateId(@PathVariable Long candidateId) {
        // Log khi lấy thông tin CV
        System.out.println("Lấy tất cả thông tin CV với ID: " + candidateId);

        // Gọi service để lấy CV
        List<CandidateCvResponse> list = candidateCvService.getCvByCandidateId(candidateId);

        // Tạo response với thông báo thành công
        ApiResponse<List<CandidateCvResponse>> apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "Danh sách CV", list);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse); // Trả về apiResponse thay vì list
    }

}
