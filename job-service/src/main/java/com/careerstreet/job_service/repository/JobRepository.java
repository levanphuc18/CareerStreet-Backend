package com.careerstreet.job_service.repository;

import com.careerstreet.job_service.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long > {
    List<Job> findAllByStatus(Long status);
    @Modifying
    @Query("UPDATE Job j SET j.views = j.views + 1 WHERE j.jobId = :jobId")
    void updateViews(@Param("jobId") Long jobId);

    @Query("SELECT j.title FROM Job j WHERE j.jobId = :jobId")
    String findJobNameByJobId(@Param("jobId") Long jobId);

    // Method tìm kiếm tất cả các job có expirationDate nhỏ hơn ngày hiện tại
    List<Job> findByExpirationDateBefore(LocalDate currentDate);
    @Query("SELECT j FROM Job j WHERE " +
            "(:title IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
            "(:location IS NULL OR LOWER(j.jobLocation) LIKE LOWER(CONCAT('%', :location, '%'))) AND " +
            "(:salaryMin IS NULL OR j.salary >= :salaryMin) AND " +
            "(:salaryMax IS NULL OR j.salary <= :salaryMax) AND " +
            "(:jobType IS NULL OR j.jobType = :jobType) AND " +
            "(:jobRank IS NULL OR j.jobRank = :jobRank) AND " +  // Thêm AND ở đây
            "(:companyName IS NULL OR j.companyName = :companyName)"
    )

    List<Job> filterJobs(
            @Param("title") String title,
            @Param("location") String location,
            @Param("salaryMin") Long salaryMin,
            @Param("salaryMax") Long salaryMax,
            @Param("jobType") String jobType,
            @Param("jobRank") String jobRank,
            @Param("companyName") String companyName);
}
