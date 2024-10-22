package com.careerstreet.candidate_service.service.implement;

//import com.careerstreet.candidate_service.client.CandidateCvClient;
import com.careerstreet.candidate_service.client.FileClient;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidateCvServiceImpl implements CandidateCvService{

    private CandidateCv candidateCv;
    private final CandidateCvRepository candidateCvRepository;
    private final CandidateRepository candidateRepository;
    private final ModelMapper modelMapper;
    private final FileClient fileClient;

    @Override
    public CandidateCvResponse createCv(CandidateCvRequest candidateCvRequest, MultipartFile file) {
        // Tìm candidate bằng ID
        Candidate candidate = candidateRepository.findById(candidateCvRequest.getCandidate_id())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        // Tạo đối tượng CandidateCv mới
        CandidateCv candidateCv = new CandidateCv();

        // Map từng trường đơn giản từ CandidateCvRequest
        candidateCv.setFullName(candidateCvRequest.getFullName());
        candidateCv.setAddress(candidateCvRequest.getAddress());
        candidateCv.setPhone(candidateCvRequest.getPhone());
        candidateCv.setEmail(candidateCvRequest.getEmail());
        candidateCv.setSchool(candidateCvRequest.getSchool());
        candidateCv.setLanguage(candidateCvRequest.getLanguage());
        candidateCv.setExperience(candidateCvRequest.getExperience());
        candidateCv.setTitle(candidateCvRequest.getTitle());
        candidateCv.setCurrentSalary(candidateCvRequest.getCurrentSalary());
        candidateCv.setPreferenceSalary(candidateCvRequest.getPreferenceSalary());
        candidateCv.setLevel(candidateCvRequest.getLevel());
        candidateCv.setPositionType(candidateCvRequest.getPositionType());
        candidateCv.setWorkLocation(candidateCvRequest.getWorkLocation());

        // Xử lý MultipartFile (file)
        System.out.println(file.getOriginalFilename() + " FileName");
        if (file != null && !file.isEmpty()) {
            MultipartFile fileTmp = file;
            ResponseEntity<Map<String, Object>> uploadResponse = fileClient.uploadFile(fileTmp);

            if (uploadResponse.getStatusCode().is2xxSuccessful()) {
                Map<String, Object> uploadResult = uploadResponse.getBody();
                String filePath = (String) uploadResult.get("secure_url");
                candidateCv.setFilePath(filePath); // Đặt filePath cho CandidateCv
            } else {
                throw new RuntimeException("File upload failed with status: " + uploadResponse.getStatusCode());
            }
        }

        candidateCv.setCandidate(candidate);

        // Lưu CandidateCv vào cơ sở dữ liệu
        candidateCv = candidateCvRepository.save(candidateCv);

        // Chuyển đổi thực thể CandidateCv thành response DTO
        CandidateCvResponse candidateCvResponse = modelMapper.map(candidateCv, CandidateCvResponse.class);
        candidateCvResponse.setCandidate_id(candidate.getCandidateId());

        return candidateCvResponse;
    }

    @Override
    public CandidateCvResponse updateCv(CandidateCvRequest candidateCvRequest, MultipartFile file, Long candidateCvId){
        CandidateCv candidateCv = candidateCvRepository.findById(candidateCvId).orElseThrow(
                () -> new EntityNotFoundException("Candidate not found with id: " + candidateCvId, GlobalCode.ERROR_ENTITY_NOT_FOUND)
        );
        // Tìm candidate bằng ID
        Candidate candidate = candidateRepository.findById(candidateCvRequest.getCandidate_id())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        // Map từng trường đơn giản từ CandidateCvRequest
        candidateCv.setFullName(candidateCvRequest.getFullName());
        candidateCv.setAddress(candidateCvRequest.getAddress());
        candidateCv.setPhone(candidateCvRequest.getPhone());
        candidateCv.setEmail(candidateCvRequest.getEmail());
        candidateCv.setSchool(candidateCvRequest.getSchool());
        candidateCv.setLanguage(candidateCvRequest.getLanguage());
        candidateCv.setExperience(candidateCvRequest.getExperience());
        candidateCv.setTitle(candidateCvRequest.getTitle());
        candidateCv.setCurrentSalary(candidateCvRequest.getCurrentSalary());
        candidateCv.setPreferenceSalary(candidateCvRequest.getPreferenceSalary());
        candidateCv.setLevel(candidateCvRequest.getLevel());
        candidateCv.setPositionType(candidateCvRequest.getPositionType());
        candidateCv.setWorkLocation(candidateCvRequest.getWorkLocation());

        // Xử lý MultipartFile (file)
        System.out.println(file.getOriginalFilename() + " FileName");
        if (file != null && !file.isEmpty()) {
            MultipartFile fileTmp = file;
            ResponseEntity<Map<String, Object>> uploadResponse = fileClient.uploadFile(fileTmp);

            if (uploadResponse.getStatusCode().is2xxSuccessful()) {
                Map<String, Object> uploadResult = uploadResponse.getBody();
                String filePath = (String) uploadResult.get("secure_url");
                candidateCv.setFilePath(filePath); // Đặt filePath cho CandidateCv
            } else {
                throw new RuntimeException("File upload failed with status: " + uploadResponse.getStatusCode());
            }
        }

        candidateCv.setCandidate(candidate);

        candidateCv = candidateCvRepository.save(candidateCv);

        CandidateCvResponse candidateCvResponse = modelMapper.map(candidateCv, CandidateCvResponse.class);
        candidateCvResponse.setCandidate_id(candidate.getCandidateId());

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
