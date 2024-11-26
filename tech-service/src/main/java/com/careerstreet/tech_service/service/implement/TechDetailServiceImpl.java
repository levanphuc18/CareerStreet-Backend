package com.careerstreet.tech_service.service.implement;

import com.careerstreet.tech_service.dto.TechDetailRequest;
import com.careerstreet.tech_service.dto.TechResponse;
import com.careerstreet.tech_service.entity.Tech;
import com.careerstreet.tech_service.entity.TechDetail;
import com.careerstreet.tech_service.entity.TechDetailId;
import com.careerstreet.tech_service.repository.TechDetailRepository;
import com.careerstreet.tech_service.repository.TechRepository;
import com.careerstreet.tech_service.service.TechDetailService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TechDetailServiceImpl implements TechDetailService {
    private final ModelMapper modelMapper;
    private final TechDetailRepository techDetailRepository;
    private final TechRepository techRepository;

    @Override
    public void saveTechDetailsForJob(TechDetailRequest techDetailRequest) {
        // Tạo danh sách các đối tượng TechDetail
        List<TechDetail> techDetails = techDetailRequest.getTechIds().stream()
                .map(techId -> {
                    // Tạo khóa chính tổng hợp TechDetailId
                    TechDetailId techDetailId = new TechDetailId(techDetailRequest.getJobId(), techId);

                    // Tạo đối tượng TechDetail với khóa chính và ánh xạ đến Tech và Job
                    Tech tech = techRepository.findById(techId)
                            .orElseThrow(() -> new RuntimeException("Tech không tìm thấy: " + techId));

                    return new TechDetail(techDetailId, tech);
                })
                .collect(Collectors.toList());

        // Lưu danh sách các TechDetail vào database
        techDetailRepository.saveAll(techDetails);
    }

    @Override
    public List<TechResponse> getTechByJobId(Long jobId){
        List<Tech> list = techDetailRepository.findTechsByJobId(jobId);

        List<TechResponse> responseList = list.stream()
                .map(tech -> modelMapper.map(tech, TechResponse.class))
                .collect(Collectors.toList());
        return responseList;
    }
}
