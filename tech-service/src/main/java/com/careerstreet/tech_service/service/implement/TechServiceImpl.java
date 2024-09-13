package com.careerstreet.tech_service.service.implement;

import com.careerstreet.tech_service.dto.TechRequest;
import com.careerstreet.tech_service.dto.TechResponse;
import com.careerstreet.tech_service.entity.Tech;
import com.careerstreet.tech_service.repository.TechRepository;
import com.careerstreet.tech_service.service.TechService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
}
