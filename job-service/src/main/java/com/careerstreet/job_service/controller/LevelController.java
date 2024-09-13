package com.careerstreet.job_service.controller;

import com.careerstreet.job_service.dto.ApiResponse;
import com.careerstreet.job_service.dto.LevelRequest;
import com.careerstreet.job_service.dto.LevelResponse;
import com.careerstreet.job_service.exception.GlobalCode;
import com.careerstreet.job_service.service.LevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/level/")
public class LevelController {
    private final LevelService levelService;

    @PostMapping("create")
    public ResponseEntity<ApiResponse<LevelResponse>> createLevel(@RequestBody LevelRequest levelRequest){
        LevelResponse levelResponse = levelService.createLevel(levelRequest);
        ApiResponse apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "Tao level thanh cong", levelResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
