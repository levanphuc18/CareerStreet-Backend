package com.careerstreet.save_service.service.Implement;

import com.careerstreet.save_service.Client.JobClient;
import com.careerstreet.save_service.dto.ApiResponse;
import com.careerstreet.save_service.dto.JobResponse;
import com.careerstreet.save_service.dto.SaveRequest;
import com.careerstreet.save_service.dto.SaveResponse;
import com.careerstreet.save_service.entity.Save;
import com.careerstreet.save_service.repository.SaveRepository;
import com.careerstreet.save_service.service.ISaveService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaveService implements ISaveService {
    private final ModelMapper modelMapper;
    private final SaveRepository saveRepository;

    private final JobClient jobClient;

    @Override
    public SaveResponse saveJob(SaveRequest saveJobRequestDTO) {

        Optional<Save> existingSave = saveRepository.findByCandidateIdAndJobId
                (saveJobRequestDTO.getCandidateId(),
                saveJobRequestDTO.getJobId());


        if (existingSave.isPresent()) {
            throw new IllegalArgumentException("Công việc này đã được lưu trước đó.");
        }

            Save save = new Save();
            save.setDate(new Date());
            save.setJobId(saveJobRequestDTO.getJobId());
            save.setCandidateId(saveJobRequestDTO.getCandidateId());
            saveRepository.save(save);
        return modelMapper.map(save, SaveResponse.class);
    }

//DÙNG ĐỂ LƯU CÔNG VIỆC
@Override
     public List<JobResponse> getSavedJobs(Long candidateId) {
         List<Save> saves = saveRepository.findByCandidateId(candidateId);
        return saves.stream().
                map(save->{
                    ApiResponse<JobResponse> job = jobClient.getJobById(save.getJobId());

                    if (job !=null && job.getData() != null) {
                        return job.getData();
                    }

                     return null;
                        }).filter(j->j !=null)
                .collect(Collectors.toList());
     }



}
