package com.careerstreet.tech_service.service.implement;

import com.careerstreet.tech_service.dto.TechRequest;
import com.careerstreet.tech_service.dto.TechResponse;
import com.careerstreet.tech_service.entity.Tech;
import com.careerstreet.tech_service.repository.TechRepository;
import com.careerstreet.tech_service.service.TechService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TechServiceImpl implements TechService {
    private final ModelMapper modelMapper;
    private final TechRepository techRepository;

    @Override
    public TechResponse createTech(TechRequest techRequest){
        Tech tech = modelMapper.map(techRequest, Tech.class);
        tech = techRepository.save(tech);

        TechResponse techResponse = modelMapper.map(tech, TechResponse.class);
        return techResponse;
    }

    @Override
    public List<TechResponse> getAllTech(){
        // Lấy tất cả các công việc từ repository
        List<Tech> techList = techRepository.findAll();

        // Chuyển đổi danh sách Job thành danh sách JobResponse
        List<TechResponse> techResponseList = techList.stream()
                .map(tech -> {
                    // Tạo đối tượng JobResponse từ đối tượng Job
                    TechResponse techResponse = new TechResponse();
                    techResponse.setTechId(tech.getTechId());
                    techResponse.setName(tech.getName());
                    // Chuyển đổi các thuộc tính khác nếu cần
                    return techResponse;
                })
                .collect(Collectors.toList());

        // Trả về danh sách JobResponse
        return techResponseList;
    }
}
