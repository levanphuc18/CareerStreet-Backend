package com.careerstreet.job_service.service.implement;

import com.careerstreet.job_service.dto.LevelRequest;
import com.careerstreet.job_service.dto.LevelResponse;
import com.careerstreet.job_service.entity.Level;
import com.careerstreet.job_service.repository.LevelRepository;
import com.careerstreet.job_service.service.LevelService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<LevelResponse> getAllLevel(){
        // Lấy tất cả các công việc từ repository
        List<Level> levelList = levelRepository.findAll();

        // Chuyển đổi danh sách Job thành danh sách JobResponse
        List<LevelResponse> levelResponseList = levelList.stream()
                .map(level -> {
                    // Tạo đối tượng JobResponse từ đối tượng Job
                    LevelResponse levelResponse = new LevelResponse();
                    levelResponse.setLevelId(level.getLevelId());
                    levelResponse.setName(level.getName());
                    // Chuyển đổi các thuộc tính khác nếu cần
                    return levelResponse;
                })
                .collect(Collectors.toList());

        // Trả về danh sách JobResponse
        return levelResponseList;
    }
}
