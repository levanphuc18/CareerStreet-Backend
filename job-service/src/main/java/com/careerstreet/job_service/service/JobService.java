package com.careerstreet.job_service.service;

import com.careerstreet.job_service.dto.JobRequest;
import com.careerstreet.job_service.dto.JobResponse;

import java.util.List;

public interface JobService {
    JobResponse createJob(JobRequest jobRequest);
    JobResponse updateJob(JobRequest jobRequest, Long jobId);
    void deleteJob(Long jobId);

    JobResponse getJobById(Long jobId);

    List<JobResponse> getJobByEmployerId(Long employerId);

    List<JobResponse> getAllJob();
    List<JobResponse> getAllJobByStatus(Long status);
    void increaseJobViews(Long jobId);
    String getJobNameById(Long jobId);

    JobResponse updateJobStatus(Long jobId, Long status);

    void updateStatusExpiration(Long jobId);
    List<JobResponse> FillterJob(String title, String location, Long salaryMin, Long salaryMax, String jobType, String jobRank,String companyName);
}
