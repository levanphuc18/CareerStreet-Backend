package com.careerstreet.job_service.service.implement;

import com.careerstreet.job_service.dto.LevelRequest;
import com.careerstreet.job_service.dto.LevelResponse;
import com.careerstreet.job_service.entity.Level;
import com.careerstreet.job_service.repository.LevelRepository;
import com.careerstreet.job_service.service.LevelService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LevelServiceImpl implements LevelService {
    private final LevelRepository levelRepository;
    private final ModelMapper modelMapper;

    @Override
    public LevelResponse createLevel(LevelRequest levelRequest){
        Level level = modelMapper.map(levelRequest, Level.class);
        level = levelRepository.save(level);
        LevelResponse levelResponse = modelMapper.map(level, LevelResponse.class);
        return levelResponse;
    }
}
