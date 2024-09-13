package com.careerstreet.admin_service.service;

import com.careerstreet.admin_service.dto.BlogRequest;
import com.careerstreet.admin_service.dto.BlogResponse;

import java.util.List;

public interface BlogService {

    BlogResponse createBlog(BlogRequest blogRequest);

    BlogResponse updateBlog(BlogRequest blogRequest, Long blogId);

//    CandidateResponse createCandidate(CandidateRequest candidateRequest);
//    CandidateResponse updateCandidateById(CandidateRequest candidateRequest, Long candidateId);
//
//    CandidateResponse getCandidateByUserName(String uername);
//    List<CandidateResponse> getAllCandidate();
}
