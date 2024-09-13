package com.careerstreet.tech_service.service.implement;

import com.careerstreet.tech_service.dto.TechDetailRequest;
import com.careerstreet.tech_service.dto.TechResponse;
import com.careerstreet.tech_service.entity.TechDetail;
import com.careerstreet.tech_service.repository.TechRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TechDetailService{
    private final ModelMapper modelMapper;
    private final TechRepository techRepository;

}
