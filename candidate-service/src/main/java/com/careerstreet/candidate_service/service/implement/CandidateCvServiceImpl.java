package com.careerstreet.candidate_service.service.implement;

import com.careerstreet.candidate_service.dto.ApiResponse;
import com.careerstreet.candidate_service.dto.CandidateCvRequest;
import com.careerstreet.candidate_service.dto.CandidateCvResponse;
import com.careerstreet.candidate_service.entity.Candidate;
import com.careerstreet.candidate_service.entity.CandidateCv;
import com.careerstreet.candidate_service.exception.EntityNotFoundException;
import com.careerstreet.candidate_service.exception.GlobalCode;
import com.careerstreet.candidate_service.repository.CandidateCvRepository;
import com.careerstreet.candidate_service.repository.CandidateRepository;
import com.careerstreet.candidate_service.service.CandidateCvService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidateCvServiceImpl implements CandidateCvService{

    private CandidateCv candidateCv;
    private final CandidateCvRepository candidateCvRepository;
    private final CandidateRepository candidateRepository;
    private final ModelMapper modelMapper;

    @Override
    public CandidateCvResponse createCv(CandidateCvRequest candidateCvRequest){
        // Tìm candidate bằng username
        Candidate candidate = candidateRepository.findById(candidateCvRequest.getCandidate_id())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        CandidateCv candidateCv = modelMapper.map(candidateCvRequest, CandidateCv.class);

        // Gán Account cho Employer
        candidateCv.setCandidate(candidate);

        candidateCv = candidateCvRepository.save(candidateCv);

        CandidateCvResponse candidateCvResponse = modelMapper.map(candidateCv, CandidateCvResponse.class);

        // Set thêm username vào CandidateResponse
        candidateCvResponse.setCandidate_id(candidate.getCandidateId());
        return candidateCvResponse;
    }

    @Override
    public CandidateCvResponse updateCv(CandidateCvRequest candidateCvRequest, Long candidateCvId){
        CandidateCv candidateCv = candidateCvRepository.findById(candidateCvId).orElseThrow(
                () -> new EntityNotFoundException("Candidate not found with id: " + candidateCvId, GlobalCode.ERROR_ENTITY_NOT_FOUND)
        );

        modelMapper.map(candidateCvRequest, candidateCv);

        candidateCv = candidateCvRepository.save(candidateCv);

        CandidateCvResponse candidateCvResponse = modelMapper.map(candidateCv, CandidateCvResponse.class);
        candidateCvResponse.setCandidate_id(candidateCv.getCandidate().getCandidateId());

        return candidateCvResponse;
    }

    @Override
    public CandidateCvResponse getCvById(Long candidateCvId){
        CandidateCv candidateCv = candidateCvRepository.findById(candidateCvId).orElseThrow(
                () -> new EntityNotFoundException("Candidate not found with id: " + candidateCvId, GlobalCode.ERROR_ENTITY_NOT_FOUND)
        );
        CandidateCvResponse candidateCvResponse = modelMapper.map(candidateCv, CandidateCvResponse.class);
        candidateCvResponse.setCandidate_id(candidateCv.getCandidate().getCandidateId());

        return candidateCvResponse;
    }

    @Override
    public void deleteCv(Long id) {
        // Kiểm tra xem CV với ID này có tồn tại không
        if (!candidateCvRepository.existsById(id)) {
            throw new EntityNotFoundException("CV không tồn tại với ID: " + id, GlobalCode.ERROR_ID_EXIST);
        }
        // Xóa CV nếu tồn tại
        candidateCvRepository.deleteById(id);
    }

    @Override
    public List<CandidateCvResponse> getCvByCandidateId(Long candidateId) {

        List<CandidateCvResponse> list = candidateCvRepository.findAll()
                .stream()
                .filter(candidateCv -> candidateCv.getCandidate().getCandidateId() == candidateId)
                .map(candidateCv -> {
                    CandidateCvResponse candidateCvResponse = modelMapper.map(candidateCv, CandidateCvResponse.class);
                    candidateCvResponse.setCandidate_id(candidateId);
                    return candidateCvResponse;
                })
                .collect(Collectors.toList());
        return list;
    }
}
