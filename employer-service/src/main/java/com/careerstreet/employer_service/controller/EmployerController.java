package com.careerstreet.employer_service.controller;

import com.careerstreet.employer_service.dto.ApiResponse;
import com.careerstreet.employer_service.dto.EmployerRequest;
import com.careerstreet.employer_service.dto.EmployerResponse;
import com.careerstreet.employer_service.exception.GlobalCode;
import com.careerstreet.employer_service.service.EmployerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/employer/")
public class EmployerController {
    private final EmployerService employerService;

    @PostMapping("create")
    public ResponseEntity<ApiResponse<EmployerResponse>> createEmployer(@RequestBody EmployerRequest employerRequest){
        System.out.println(" Cbi Tao nhà tuyển dụng ");
        EmployerResponse employerResponse = employerService.createEmployer(employerRequest);

        ApiResponse apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "Tạo nhà tuyển dụng thành công", employerRequest);

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse<EmployerResponse>> updateEmployer(@RequestBody EmployerRequest employerRequest, @PathVariable Long id){
        System.out.println(" Cbi cap nhat nhà tuyển dụng ");
        EmployerResponse employerResponse = employerService.updateEmployerById(employerRequest,id);

        ApiResponse apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "Cập nhật nhà tuyển dụng thành cng", employerResponse);

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("getbyusername/{username}")
    public ResponseEntity<ApiResponse<EmployerResponse>> getEmployer(@PathVariable String username){
        EmployerResponse employerResponse = employerService.getEmployerByUserName(username);
        ApiResponse apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "lấy tt nhà tuyển dụng thành công", employerResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("list/all")
    public ResponseEntity<ApiResponse<List<EmployerResponse>>> getAllEmployer(){
        List<EmployerResponse> list = employerService.getAllEmployer();
        ApiResponse apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "Lay tat ca nha tuyen dung", list);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("getidbyusername/{username}")
    public ResponseEntity<ApiResponse<Long>>getEmployerId(@PathVariable String username){
        System.out.println( "id nha tuyen dung");
        Long employer_id = employerService.getEployerIdByUsername(username);
        System.out.println( "id ung vien 1 "+ employer_id);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS, "Id nha tuyen dung", employer_id);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("getemployer/{employerId}")
    public ResponseEntity<EmployerResponse>getEmployerById(@PathVariable Long employerId){
        EmployerResponse employerResponse = employerService.getEmployerById(employerId);
        System.out.println( "Cong ty vơi Id "+ employerId + " la: " + employerResponse.getAddress());
//        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS, "Ten cong ty", conpany_name);
        return ResponseEntity.status(HttpStatus.OK).body(employerResponse);
    }
}
