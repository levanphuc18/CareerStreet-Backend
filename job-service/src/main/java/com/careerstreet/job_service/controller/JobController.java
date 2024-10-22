package com.careerstreet.job_service.controller;

import com.careerstreet.job_service.dto.ApiResponse;
import com.careerstreet.job_service.dto.JobRequest;
import com.careerstreet.job_service.dto.JobResponse;
import com.careerstreet.job_service.exception.GlobalCode;
import com.careerstreet.job_service.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/job/")
public class JobController {
    private final JobService jobService;

    @PostMapping("create")
    public ResponseEntity<ApiResponse<JobResponse>> createJob(@RequestBody JobRequest jobRequest){
        System.out.println("CB tao job");
        JobResponse jobResponse = jobService.createJob(jobRequest);
        ApiResponse apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "Tạo công việc thành công", jobResponse);
        System.out.println("tạo công việc thành công");
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping("update/{jobId}")
    public ResponseEntity<ApiResponse<JobResponse>> updateJob(@RequestBody JobRequest jobRequest, @PathVariable Long jobId){
        System.out.println("update job");
        JobResponse jobResponse = jobService.updateJob(jobRequest, jobId);
        ApiResponse apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "Cập nhật công việc thành công", jobResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<ApiResponse<JobResponse>> getJobById(@PathVariable Long id) {
        // Log khi lấy thông tin CV
        System.out.println("Lấy thông tin Job với ID: " + id);

        // +1 views
        jobService.increaseJobViews(id);
        // Gọi service để lấy CV
        JobResponse jobResponse = jobService.getJobById(id);
        // Tạo response với thông báo thành công
        ApiResponse<JobResponse> apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "Lấy thông tin Job thành công", jobResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }


    @GetMapping("getJobByEmployer/{employerId}")
    public ResponseEntity<ApiResponse<List<JobResponse>>> getAllJobByemployerId(@PathVariable Long employerId) {
        // Log khi lấy thông tin job
        System.out.println("Lấy tat ca thông tin job cua ntd: " + employerId);

        // Gọi service để lấy job
        List<JobResponse> list = jobService.getJobByEmployerId(employerId);

        // Tạo response với thông báo thành công
        ApiResponse apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "Danh sach job cua ntd", list);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("getall")
    public ResponseEntity<ApiResponse<List<JobResponse>>> getAllJob() {
        // Log khi lấy thông tin CV
        System.out.println("Lấy tat ca thông tin job ");

        // Gọi service để lấy CV
        List<JobResponse> list = jobService.getAllJob();

        // Tạo response với thông báo thành công
        ApiResponse apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "Danh sach tat ca job", list);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("getall/status/{status}")
    public ResponseEntity<ApiResponse<List<JobResponse>>> getAllJobByStatus(@PathVariable Long status) {
        // Log khi lấy thông tin CV
        System.out.println("Lấy tat ca thông tin job bang status: " + status);

        // Gọi service để lấy CV
        List<JobResponse> list = jobService.getAllJobByStatus(status);

        // Tạo response với thông báo thành công
        ApiResponse apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "Danh sach tat ca job thuộc status", list);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
