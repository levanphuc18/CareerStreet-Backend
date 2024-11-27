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
import jakarta.transaction.Transactional;
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
        Level level = levelRepository.findById(jobRequest.getLevelId())
                .orElseThrow(() -> new RuntimeException("Level not found"));

        Job job = modelMapper.map(jobRequest, Job.class);

        job.setLevel(level);

        job = jobRepository.save(job);

        JobResponse jobResponse = modelMapper.map(job, JobResponse.class);

        jobResponse.setLevelId(level.getLevelId());

        return jobResponse;
    }

    @Override
    public JobResponse updateJob(JobRequest jobRequest, Long jobId){
        // lấy level set vào job để lấy id level
        Level level = levelRepository.findById(jobRequest.getLevelId())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
        modelMapper.map(jobRequest,job);
        job.setLevel(level);
        job = jobRepository.save(job);
        JobResponse jobResponse = modelMapper.map(job, JobResponse.class);
        jobResponse.setLevelId(level.getLevelId());
        jobResponse.setLevelName(level.getName());
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
        jobResponse.setLevelId(job.getLevel().getLevelId());
        jobResponse.setLevelName(job.getLevel().getName());
        jobResponse.setStatus(job.getStatus());

//        // tim employer dua vao id
//        EmployerResponse employerResponse = employerClient.getEmployerById(job.getEmployerId()).getBody();
//        // gan ten cong ty vao job
//        jobResponse.setCompanyName(employerResponse.getCompany());

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
                    jobResponse.setLevelId(job.getLevel().getLevelId());
                    jobResponse.setLevelName(job.getLevel().getName());
                    jobResponse.setStatus(job.getStatus());

//                    // tim employer dua vao id
//                    EmployerResponse employerResponse = employerClient.getEmployerById(job.getEmployerId()).getBody();
//                    // gan ten cong ty vao job
//                    jobResponse.setCompanyName(employerResponse.getCompany());

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
                    jobResponse.setCompanyName(job.getCompanyName()); // Lấy tên công ty từ job
                    jobResponse.setNumberOfEmployees(job.getNumberOfEmployees()); // Thêm số lượng nhân viên
                    jobResponse.setCompanyWebsite(job.getCompanyWebsite()); // Thêm trang web công ty
                    jobResponse.setCompanyOverview(job.getCompanyOverview()); // Thêm giới thiệu công ty
                    jobResponse.setTitle(job.getTitle());
                    jobResponse.setJobLocation(job.getJobLocation()); // Sửa lại để lấy jobLocation
                    jobResponse.setSalary(job.getSalary());
                    jobResponse.setNumberOfRecruitment(job.getNumberOfRecruitment());
                    jobResponse.setJobDescription(job.getJobDescription()); // Sửa lại để lấy jobDescription
                    jobResponse.setJobRequirements(job.getJobRequirements()); // Sửa lại để lấy jobRequirements
                    jobResponse.setBenefits(job.getBenefits()); // Thêm thông tin về lợi ích
                    jobResponse.setEducationLevel(job.getEducationLevel()); // Thêm trình độ học vấn
                    jobResponse.setJobRank(job.getJobRank()); // Thêm cấp bậc công việc
                    jobResponse.setJobType(job.getJobType()); // Thêm loại công việc
                    jobResponse.setGender(job.getGender()); // Thêm thông tin giới tính
                    jobResponse.setContactPerson(job.getContactPerson()); // Thêm người liên hệ
                    jobResponse.setContactPhone(job.getContactPhone()); // Thêm điện thoại liên hệ
                    jobResponse.setContactEmail(job.getContactEmail()); // Thêm email liên hệ
                    jobResponse.setContactAddress(job.getContactAddress()); // Thêm địa chỉ liên hệ
                    jobResponse.setPostingDate(job.getPostingDate());
                    jobResponse.setExpirationDate(job.getExpirationDate()); // Thêm ngày hết hạn
                    jobResponse.setEmployerId(job.getEmployerId());
//                    jobResponse.setTechDetailId(job.getTechDetailId());
                    jobResponse.setViews(job.getViews());
                    jobResponse.setStatus(job.getStatus());

                    // gán levelID cho job
                    Level level = levelRepository.findById(job.getLevel().getLevelId())
                            .orElseThrow(() -> new RuntimeException("Level not found"));
                    jobResponse.setLevelId(level.getLevelId());
                    // get Level name
                    jobResponse.setLevelName(level.getName());

//                // Tìm employer dựa vào id
//                    EmployerResponse employerResponse = employerClient.getEmployerById(job.getEmployerId()).getBody();
//                // Gán tên công ty vào job
//                    jobResponse.setCompanyName(employerResponse.getCompany());

                // Chuyển đổi các thuộc tính khác nếu cần
                    return jobResponse;

                })
                .collect(Collectors.toList());

        // Trả về danh sách JobResponse
        return jobResponseList;
    }

    @Override
    public List<JobResponse> getAllJobByStatus(Long status){
        // Lấy tất cả các công việc từ repository với status
        List<JobResponse> jobResponseList = jobRepository.findAllByStatus(status)
                .stream()
                .map(job -> {
                    JobResponse jobResponse = new JobResponse();
                    jobResponse.setJobId(job.getJobId());
                    jobResponse.setCompanyName(job.getCompanyName()); // Lấy tên công ty từ job
                    jobResponse.setNumberOfEmployees(job.getNumberOfEmployees()); // Thêm số lượng nhân viên
                    jobResponse.setCompanyWebsite(job.getCompanyWebsite()); // Thêm trang web công ty
                    jobResponse.setCompanyOverview(job.getCompanyOverview()); // Thêm giới thiệu công ty
                    jobResponse.setTitle(job.getTitle());
                    jobResponse.setJobLocation(job.getJobLocation()); // Lấy jobLocation
                    jobResponse.setSalary(job.getSalary());
                    jobResponse.setNumberOfRecruitment(job.getNumberOfRecruitment());
                    jobResponse.setJobDescription(job.getJobDescription()); // Lấy jobDescription
                    jobResponse.setJobRequirements(job.getJobRequirements()); // Lấy jobRequirements
                    jobResponse.setBenefits(job.getBenefits()); // Thêm thông tin về lợi ích
                    jobResponse.setEducationLevel(job.getEducationLevel()); // Thêm trình độ học vấn
                    jobResponse.setJobRank(job.getJobRank()); // Thêm cấp bậc công việc
                    jobResponse.setJobType(job.getJobType()); // Thêm loại công việc
                    jobResponse.setGender(job.getGender()); // Thêm thông tin giới tính
                    jobResponse.setContactPerson(job.getContactPerson()); // Thêm người liên hệ
                    jobResponse.setContactPhone(job.getContactPhone()); // Thêm điện thoại liên hệ
                    jobResponse.setContactEmail(job.getContactEmail()); // Thêm email liên hệ
                    jobResponse.setContactAddress(job.getContactAddress()); // Thêm địa chỉ liên hệ
                    jobResponse.setPostingDate(job.getPostingDate());
                    jobResponse.setExpirationDate(job.getExpirationDate()); // Thêm ngày hết hạn
                    jobResponse.setEmployerId(job.getEmployerId());
//                    jobResponse.setTechDetailId(job.getTechDetailId());
                    jobResponse.setViews(job.getViews());
                    jobResponse.setStatus(job.getStatus());

                    // Gán levelID cho job
                    Level level = levelRepository.findById(job.getLevel().getLevelId())
                            .orElseThrow(() -> new RuntimeException("Level not found"));
                    jobResponse.setLevelId(level.getLevelId());
                    // Get Level name
                    jobResponse.setLevelName(level.getName());

//                    // Tìm employer dựa vào id
//                    EmployerResponse employerResponse = employerClient.getEmployerById(job.getEmployerId()).getBody();
//                    // Gán tên công ty vào job
//                    jobResponse.setCompanyName(employerResponse.getCompany());

                    return jobResponse;
                })
                .collect(Collectors.toList());

        // Trả về danh sách JobResponse
        return jobResponseList;
    }

    @Override
    @Transactional
    public void increaseJobViews(Long jobId){
        jobRepository.updateViews(jobId);
    }

    @Override
    public String getJobNameById(Long jobId){
        String jobName= jobRepository.findJobNameByJobId(jobId);
        return jobName;
    }

    @Override
    public JobResponse updateJobStatus(Long jobId, Long status){
        Job job = jobRepository.findById(jobId).orElseThrow(
                () -> new EntityNotFoundException("Khong tim thay thong tịn cong viec" , GlobalCode.ERROR_ENTITY_NOT_FOUND)
        );
        job.setStatus(status);
        job = jobRepository.save(job);
        JobResponse jobResponse = modelMapper.map(job, JobResponse.class);
        return jobResponse;
    }

    @Override
    public void updateStatusExpiration(Long jobId){
        Job job = jobRepository.findById(jobId).orElseThrow(
                () -> new EntityNotFoundException("Khong tim thay thong tịn cong viec" , GlobalCode.ERROR_ENTITY_NOT_FOUND)
        );
        job.setStatus(2L);
        jobRepository.save(job);
    }
    @Override
    public List<JobResponse> FillterJob(String title, String location, Long salaryMin, Long salaryMax, String jobType, String jobRank,String companyName)
    {
        return jobRepository.filterJobs(title, location, salaryMin, salaryMax, jobType, jobRank,companyName)
                .stream()
                .filter(job -> job.getStatus()==1)
                .map(job ->{
                    JobResponse response = new JobResponse();
                    response.setJobId(job.getJobId());
                    response.setTitle(job.getTitle());
                    response.setJobLocation(job.getJobLocation());
                    response.setSalary(job.getSalary());
                    response.setJobType(job.getJobType());
                    response.setJobRank(job.getJobRank());
                    response.setCompanyName(job.getCompanyName());
                    return response;
                } )
                .collect(Collectors.toList());
    }
}
