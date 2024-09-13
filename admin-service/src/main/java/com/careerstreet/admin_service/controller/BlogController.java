package com.careerstreet.admin_service.controller;

import com.careerstreet.admin_service.dto.ApiResponse;
import com.careerstreet.admin_service.dto.BlogRequest;
import com.careerstreet.admin_service.dto.BlogResponse;
import com.careerstreet.admin_service.entity.Blog;
import com.careerstreet.admin_service.exception.GlobalCode;
import com.careerstreet.admin_service.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/blog/")
public class BlogController {
    private final BlogService blogService;

    @PostMapping("create")
    public ResponseEntity<ApiResponse<BlogResponse>> createBlog(@RequestBody BlogRequest blogRequest){
        System.out.println("Cbi tao blog");
        BlogResponse blogResponse = blogService.createBlog(blogRequest);
        ApiResponse apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "Tao Blog thanh cong", blogResponse);

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping("update/{blogId}")
    public ResponseEntity<ApiResponse<BlogResponse>> createBlog(@RequestBody BlogRequest blogRequest, @PathVariable Long blogId){
        System.out.println("Cbi cap nhat blog");
        BlogResponse blogResponse = blogService.updateBlog(blogRequest, blogId);
        ApiResponse apiResponse = new ApiResponse<>(GlobalCode.SUCCESS, "Cap nhat Blog thanh cong", blogResponse);

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
