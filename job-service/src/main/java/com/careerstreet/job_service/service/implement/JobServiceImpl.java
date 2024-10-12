package com.careerstreet.job_service.service.implement;

import com.careerstreet.job_service.client.EmployerClient;
import com.careerstreet.job_service.dto.EmployerResponse;
import com.careerstreet.job_service.dto.JobRequest;
import com.careerstreet.job_service.dto.JobResponse;
import com.careerstreet.job_service.entity.Job;
import com.careerstreet.job_service.entity.Level;
import com.careerstreet.job_service.exception.EntityNotFoundException;
import com.careerstreet.job_service.exception.GlobalCode;
import com.careerstreet.job_service.repository.JobRepository;
import com.careerstreet.job_service.repository.LevelRepository;
import com.careerstreet.job_service.service.JobService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final LevelRepository levelRepository;
    private final ModelMapper modelMapper;

    private final EmployerClient employerClient;

    @Override
    public JobResponse createJob(JobRequest jobRequest){
        Level level = levelRepository.findById(jobRequest.getLevel_id())
                .orElseThrow(() -> new RuntimeException("Level not found"));

        Job job = modelMapper.map(jobRequest, Job.class);

        job.setLevel(level);

        job = jobRepository.save(job);

        JobResponse jobResponse = modelMapper.map(job, JobResponse.class);

        jobResponse.setLevel_id(level.getLevelId());

        return jobResponse;
    }

    @Override
    public JobResponse updateJob(JobRequest jobRequest, Long jobId){
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
        modelMapper.map(jobRequest,job);
        job = jobRepository.save(job);
        JobResponse jobResponse = modelMapper.map(job, JobResponse.class);
        return jobResponse;
    }

    @Override
    public void deleteJob(Long jobId){
        // Kiểm tra xem job với ID này có tồn tại không
        if (!jobRepository.existsById(jobId)) {
            throw new EntityNotFoundException("CV không tồn tại với ID: " + jobId, GlobalCode.ERROR_ID_EXIST);
        }
        // Xóa nếu tồn tại
        jobRepository.deleteById(jobId);
    }

    @Override
    public JobResponse getJobById(Long jobId){
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        JobResponse jobResponse = modelMapper.map(job, JobResponse.class);
        jobResponse.setLevel_id(job.getLevel().getLevelId());
        jobResponse.setLevelName(job.getLevel().getName());

        // tim employer dua vao id
        EmployerResponse employerResponse = employerClient.getEmployerById(job.getEmployerId()).getBody();
        // gan ten cong ty vao job
        jobResponse.setCompanyName(employerResponse.getCompany());

        return jobResponse;
    }

    @Override
    public List<JobResponse> getJobByEmployerId(Long employerId){
        List<JobResponse> list = jobRepository.findAll()
                .stream()
                .filter(job -> job.getEmployerId() == employerId)
                .map(job -> {
                    JobResponse jobResponse = modelMapper.map(job, JobResponse.class);
                    jobResponse.setEmployerId(employerId);
                    jobResponse.setLevel_id(job.getLevel().getLevelId());
                    jobResponse.setLevelName(job.getLevel().getName());

                    // tim employer dua vao id
                    EmployerResponse employerResponse = employerClient.getEmployerById(job.getEmployerId()).getBody();
                    // gan ten cong ty vao job
                    jobResponse.setCompanyName(employerResponse.getCompany());

                    return jobResponse;
                })
                .collect(Collectors.toList());
        return list;
    }

    @Override
    public List<JobResponse> getAllJob(){

        // Lấy tất cả các công việc từ repository
        List<Job> jobList = jobRepository.findAll();


        // Chuyển đổi danh sách Job thành danh sách JobResponse
        List<JobResponse> jobResponseList = jobList.stream()
                .map(job -> {
                    // Tạo đối tượng JobResponse từ đối tượng Job
                    JobResponse jobResponse = new JobResponse();
                    jobResponse.setJobId(job.getJobId());
                    jobResponse.setTitle(job.getTitle());
                    jobResponse.setDescription(job.getDescription());
                    jobResponse.setRequirement(job.getRequirement());
                    jobResponse.setSalary(job.getSalary());
                    jobResponse.setAddress(job.getAddress());
                    jobResponse.setPositionType(job.getPositionType());
                    jobResponse.setPostingDate(job.getPostingDate());
                    jobResponse.setDeadline(job.getDeadline());
                    jobResponse.setTechDetailId(job.getTechDetailId());
                    jobResponse.setEmployerId(job.getEmployerId());
                    jobResponse.setLevel_id(job.getLevel().getLevelId());
                    jobResponse.setLevelName(job.getLevel().getName());

                    // tim employer dua vao id
                    EmployerResponse employerResponse = employerClient.getEmployerById(job.getEmployerId()).getBody();
                    // gan ten cong ty vao job
                    jobResponse.setCompanyName(employerResponse.getCompany());

                    // Chuyển đổi các thuộc tính khác nếu cần
                    return jobResponse;
                })
                .collect(Collectors.toList());

        // Trả về danh sách JobResponse
        return jobResponseList;
    }
}
