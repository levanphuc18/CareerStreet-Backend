package com.careerstreet.candidate_service.controller;


import com.careerstreet.candidate_service.dto.ApiResponse;
import com.careerstreet.candidate_service.dto.CandidateCvResponse;
import com.careerstreet.candidate_service.dto.CandidateRequest;
import com.careerstreet.candidate_service.dto.CandidateResponse;
import com.careerstreet.candidate_service.exception.GlobalCode;
import com.careerstreet.candidate_service.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/candidate/")
public class CandidateController {
    private final CandidateService candidateService;

    @PostMapping("create")
    public ResponseEntity<ApiResponse<CandidateResponse>> createCandidate (@RequestBody CandidateRequest candidateRequest){
        System.out.println(" Cbi Tao ung vien ");
        CandidateResponse candidateResponse= candidateService.createCandidate(candidateRequest);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS, "Tạo ứng viên thành công", candidateResponse);
        System.out.println("Tao ung vien thanh cong rsp New");
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse<CandidateResponse>> updateCandidate (@RequestBody CandidateRequest candidateRequest, @PathVariable Long id){
        System.out.println(" Cbi cap nhat tao ung vien ");
        CandidateResponse candidateResponse= candidateService.updateCandidateById(candidateRequest, id);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS, "cap nhat ung vien thanh cong rsp New", candidateResponse);
        System.out.println("cap nhat ung vien thanh cong rsp New");
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("getbyusername/{username}")
    public ResponseEntity<ApiResponse<CandidateResponse>>getCandidate(@PathVariable String username){
        CandidateResponse candidateResponse = candidateService.getCandidateByUserName(username);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS, "Thong tin ứng viên", candidateResponse);
//        candidateResponse.setEmail(candidateService.getEmailByUsername(username));
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("getidbyusername/{username}")
    public ResponseEntity<ApiResponse<Long>>getCandidateId(@PathVariable String username){
        System.out.println( "id ung vien");
        Long candidate_id = candidateService.getCandidateIdByUsername(username);
        System.out.println( "id ung vien: "+ candidate_id);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS, "Id ứng viên", candidate_id);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("list/all")
    public ResponseEntity<ApiResponse<List<CandidateResponse>>>getAllCandidate(){
        System.out.println("get all candidate");
       List<CandidateResponse> list = candidateService.getAllCandidate();
       ApiResponse apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "Lay tat ca danh sach ung vien", list);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
