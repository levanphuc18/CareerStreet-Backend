package com.careerstreet.apply_service.controller;

import com.careerstreet.apply_service.dto.ApiResponse;
import com.careerstreet.apply_service.dto.ApplyRequest;
import com.careerstreet.apply_service.dto.ApplyResponse;
import com.careerstreet.apply_service.exception.GlobalCode;
//import com.careerstreet.apply_service.kafka.producer.ApplyKafkaProducer;
import com.careerstreet.apply_service.service.ApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/apply/")
public class ApplyController {
    private final ApplyService applyService;
//    private final ApplyKafkaProducer applyKafkaProducer;
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
        // Sinh UUID cho message Kafka
        String uuid = UUID.randomUUID().toString();
        // Chuyển đổi OrderResponse thành OrderKafka (có thể cần ánh xạ hoặc tạo đối tượng mới)
//        applyKafka.setUuid(uuid);
        // Bạn có thể cần ánh xạ các thuộc tính từ orderResponse sang orderKafka nếu cần
        applyResponse.setCandidateCvId(applyResponse.getCandidateCvId());
        applyResponse.setJobId(applyResponse.getJobId());
        applyResponse.setDate(applyResponse.getDate());
        applyResponse.setStatus(applyResponse.getStatus());

//        applyKafkaProducer.writeToKafka(applyResponse, uuid);
        System.out.println("update status apply "+ applyResponse);
        ApiResponse apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "cap nhat trang thai thanh cong", applyResponse);

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("getJobByStatus/{status}")
    public ResponseEntity<ApiResponse<List<ApplyResponse>>> getJobByStatus(@PathVariable int status) {
        // Log khi lấy thông tin CV
        System.out.println("Lấy tat ca apply thuoc status: " + status);

        // Gọi service để lấy job
        List<ApplyResponse> list = applyService.getListApplyByStatus(status);

        // Tạo response với thông báo thành công
        ApiResponse apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "Danh sach apply thuoc status", list);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
