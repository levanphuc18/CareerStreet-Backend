package com.careerstreet.apply_service.service.implement;

import com.careerstreet.apply_service.dto.ApplyRequest;
import com.careerstreet.apply_service.dto.ApplyResponse;
import com.careerstreet.apply_service.entity.Apply;
import com.careerstreet.apply_service.exception.EntityNotFoundException;
import com.careerstreet.apply_service.exception.GlobalCode;
import com.careerstreet.apply_service.repository.ApplyRepository;
import com.careerstreet.apply_service.service.ApplyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplyServiceImpl implements ApplyService {
    private final ModelMapper modelMapper;
    private final ApplyRepository applyRepository;

    @Override
    public ApplyResponse createApply(ApplyRequest applyRequest){
        Apply apply = modelMapper.map(applyRequest, Apply.class);
        apply = applyRepository.save(apply);
        ApplyResponse applyResponse = modelMapper.map(apply, ApplyResponse.class);
        return applyResponse;
    }

    @Override
    public ApplyResponse updateApplyStatus(Long id, int status){
        Apply apply = applyRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Khong tim thay thong tin cv", GlobalCode.ERROR_ENTITY_NOT_FOUND)

                );
        apply.setStatus(status);
        apply = applyRepository.save(apply);

        ApplyResponse applyResponse = modelMapper.map(apply, ApplyResponse.class);
        return applyResponse;
    }

    @Override
    public List<ApplyResponse> getListApplyByStatus(int status){
        List<ApplyResponse> list= applyRepository.findAll()
                .stream()
                .filter(apply -> apply.getStatus() == status)
                .map(apply -> {
                    ApplyResponse applyResponse = modelMapper.map(apply, ApplyResponse.class);
                    applyResponse.setStatus(status);
                    return applyResponse;
                })
                .collect(Collectors.toList());
        return list;
    }
}
