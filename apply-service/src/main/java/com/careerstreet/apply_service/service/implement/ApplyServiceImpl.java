package com.careerstreet.apply_service.service.implement;

import com.careerstreet.apply_service.client.CandidateCvClient;
import com.careerstreet.apply_service.dto.ApiResponse;
import com.careerstreet.apply_service.dto.ApplyRequest;
import com.careerstreet.apply_service.dto.ApplyResponse;
import com.careerstreet.apply_service.dto.CandidateCvResponse;
import com.careerstreet.apply_service.entity.Apply;
import com.careerstreet.apply_service.exception.EntityNotFoundException;
import com.careerstreet.apply_service.exception.GlobalCode;
import com.careerstreet.apply_service.repository.ApplyRepository;
import com.careerstreet.apply_service.service.ApplyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplyServiceImpl implements ApplyService {
    private final ModelMapper modelMapper;
    private final ApplyRepository applyRepository;
    private final CandidateCvClient candidateCvClient;

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

    @Override
    public List<Apply> getAppliesByCandidateId(Long candidateId) {
        // Gọi FeignClient để lấy danh sách CandidateCv dựa trên candidateId
        ApiResponse<List<CandidateCvResponse>> response = candidateCvClient.getCandidateCvBycandidateId(candidateId);

        // Kiểm tra mã trạng thái và lấy danh sách CandidateCv từ ApiResponse
        List<CandidateCvResponse> candidateCvs = response.getData(); // Lấy danh sách từ trường data

        // Tạo danh sách để chứa kết quả Apply
        List<Apply> applyList = new ArrayList<>();

        // Duyệt qua danh sách CandidateCv để lấy apply cho từng candidateCvId
        for (CandidateCvResponse candidateCv : candidateCvs) {
            List<Apply> applies = applyRepository.findByCandidateCvId(candidateCv.getCandidateCvId());
            applyList.addAll(applies);
        }
        return applyList;
    }


}
