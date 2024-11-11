package com.careerstreet.job_service.schedule;

import com.careerstreet.job_service.entity.Job;
import com.careerstreet.job_service.repository.JobRepository;
import com.careerstreet.job_service.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Component
@RequiredArgsConstructor
public class JobSchedule {
    private final JobRepository jobRepository;
    private final JobService jobService;

    // Lịch trình chạy vào lúc 2 giờ sáng mỗi ngày
    @Scheduled(cron = "0 0 2 * * *")
    public void updateExpiredJobStatus() {
        // Lấy ngày hiện tại
        LocalDate currentDate = LocalDate.now();

        // Tìm tất cả các job có expirationDate nhỏ hơn ngày hiện tại
        List<Job> expiredJobs = jobRepository.findByExpirationDateBefore(currentDate);

        // Cập nhật trạng thái cho các job đã hết hạn
        expiredJobs.forEach(job -> {
            jobService.updateStatusExpiration(job.getJobId());
        });
    }
}
