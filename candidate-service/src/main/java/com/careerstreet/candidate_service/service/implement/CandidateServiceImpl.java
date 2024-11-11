package com.careerstreet.candidate_service.service.implement;

//import com.careerstreet.candidate_service.client.NotificationClient;
import com.careerstreet.candidate_service.dto.CandidateRequest;
import com.careerstreet.candidate_service.dto.CandidateResponse;
import com.careerstreet.candidate_service.entity.Candidate;
import com.careerstreet.candidate_service.exception.EntityNotFoundException;
import com.careerstreet.candidate_service.exception.GlobalCode;
import com.careerstreet.candidate_service.repository.CandidateRepository;
import com.careerstreet.candidate_service.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {
    private final ModelMapper modelMapper;
    private final CandidateRepository candidateRepository;
//    private final NotificationClient notificationClient;

    @Override
    public CandidateResponse createCandidate(CandidateRequest candidateRequest){
        //        boolean checkPhone = candidateRepository.existsByPhone(candidateRequest.getPhone());
//        if(checkPhone){
//            throw new EntityExitsException("SDT da dang ky truoc do", GlobalCode.ERROR_ID_EXIST);
//        }

        Candidate candidate = modelMapper.map(candidateRequest, Candidate.class);

        candidate = candidateRepository.save(candidate);
        CandidateResponse candidateResponse = modelMapper.map(candidate, CandidateResponse.class);

        return  candidateResponse;
    }

    @Override
    public CandidateResponse updateCandidateById(CandidateRequest candidateRequest, Long candidateId){
        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow(
                () -> new EntityNotFoundException("Candidate not found with id: " + candidateId, GlobalCode.ERROR_ENTITY_NOT_FOUND)
        );

        // Sao chép các giá trị từ CustomerRequest sang Customer hiện tại
        modelMapper.map(candidateRequest, candidate);

        // Lưu thực thể đã cập nhật vào cơ sở dữ liệu
        candidate = candidateRepository.save(candidate);

        // Chuyển đổi thành CustomerResponse
        CandidateResponse candidateResponse = modelMapper.map(candidate, CandidateResponse.class);

        return candidateResponse;
    }

    @Override
    public CandidateResponse getCandidateByUserName(String username) {
        Candidate candidate = candidateRepository.findCandidateByUsername(username);
        System.out.println("candidate ID: "+candidate.getCandidateId());
        CandidateResponse candidateResponse = modelMapper.map(candidate, CandidateResponse.class);
        return candidateResponse;
    }

    @Override
    public Long getCandidateIdByUsername (String username){
        System.out.println(candidateRepository.findCandidateByUsername(username) + "phuc");
        Candidate candidate = candidateRepository.findCandidateByUsername(username);
        Long id = candidate.getCandidateId();
        return id;
    }

    @Override
    public List<CandidateResponse> getAllCandidate() {
        List<Candidate> list = candidateRepository.findAll();
        return list.stream()
                .map(candidate -> {
                    CandidateResponse candidateResponse = modelMapper.map(candidate, CandidateResponse.class);
                    return candidateResponse;
                })
                .collect(Collectors.toList());

    }
}
