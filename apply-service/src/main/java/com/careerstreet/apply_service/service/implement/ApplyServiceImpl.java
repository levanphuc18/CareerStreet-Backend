package com.careerstreet.apply_service.service.implement;

import com.careerstreet.apply_service.client.CandidateCvClient;
import com.careerstreet.apply_service.client.JobClient;
import com.careerstreet.apply_service.client.NotificationClient;
import com.careerstreet.apply_service.dto.*;
import com.careerstreet.apply_service.entity.Apply;
import com.careerstreet.apply_service.exception.EntityNotFoundException;
import com.careerstreet.apply_service.exception.GlobalCode;
import com.careerstreet.apply_service.repository.ApplyRepository;
import com.careerstreet.apply_service.service.ApplyService;
import com.careerstreet.event.NotificationEvent;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplyServiceImpl implements ApplyService {
    private final ModelMapper modelMapper;
    private final ApplyRepository applyRepository;
    private final CandidateCvClient candidateCvClient;
    private final JobClient jobClient;
    private final NotificationClient notificationClient;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public ApplyResponse createApply(ApplyRequest applyRequest) {
        Apply apply = modelMapper.map(applyRequest, Apply.class);
        apply = applyRepository.save(apply);
        ApplyResponse applyResponse = modelMapper.map(apply, ApplyResponse.class);
        return applyResponse;
    }

    @Override
    public ApplyResponse updateApplyStatus(Long id, int status) {
        Apply apply = applyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy thông tin ứng tuyển", GlobalCode.ERROR_ENTITY_NOT_FOUND));

        // Cập nhật trạng thái ứng tuyển
        apply.setStatus(status);

        // Lưu lại thay đổi trạng thái vào cơ sở dữ liệu
        apply = applyRepository.save(apply);

        // Lấy thông tin CV của ứng viên từ dịch vụ Candidate CV
        CandidateCvResponse candidateCvResponse = candidateCvClient.getCvById(apply.getCandidateCvId()).getBody().getData();
        System.out.println("CV: " + candidateCvResponse);
        System.out.println("Recipient: " + candidateCvResponse.getEmail());

        // Khởi tạo thông tin  notification
        NotificationEvent notificationEvent = new NotificationEvent();
        notificationEvent.setRecipient(candidateCvResponse.getEmail());

        // Thiết lập nội dung và tiêu đề email dựa vào trạng thái
        switch (status) {
            case 0: // Chờ xét duyệt
                notificationEvent.setSubject("Thông báo: Chờ xét duyệt");
                notificationEvent.setMsgBody("Đơn ứng tuyển của bạn đang ở trạng thái chờ xét duyệt. Chúng tôi sẽ thông báo sớm nhất khi có kết quả.");
                break;
            case 1: // Đang được xem xét
                notificationEvent.setSubject("Thông báo: Đơn ứng tuyển đang được xem xét");
                notificationEvent.setMsgBody("Đơn ứng tuyển của bạn đang được xem xét bởi nhà tuyển dụng. Xin vui lòng đợi kết quả.");
                break;
            case 2: // Đang chờ phỏng vấn
                notificationEvent.setSubject("Lời mời phỏng vấn từ CarrerStreet");
                notificationEvent.setMsgBody("Chúc mừng! Bạn đã vượt qua vòng xét duyệt và được mời tham gia phỏng vấn. Xin vui lòng chuẩn bị sẵn sàng.");
                break;
            case 3: // Phỏng vấn xong
                notificationEvent.setSubject("Kết quả phỏng vấn của bạn");
                notificationEvent.setMsgBody("Phỏng vấn của bạn đã kết thúc. Chúng tôi sẽ liên hệ với bạn sớm nhất để thông báo kết quả.");
                break;
            case 4: // Đang chờ quyết định
                notificationEvent.setSubject("Đơn ứng tuyển đang chờ quyết định");
                notificationEvent.setMsgBody("Đơn ứng tuyển của bạn hiện đang được đánh giá cuối cùng. Xin vui lòng đợi quyết định của chúng tôi.");
                break;
            case 5: // Đã tuyển dụng
                notificationEvent.setSubject("Chúc mừng! Bạn đã được tuyển dụng");
                notificationEvent.setMsgBody("Chúc mừng bạn đã chính thức được nhận vào vị trí công việc bạn đã ứng tuyển tại CarrerStreet!");
                break;
            case -1: // Bị từ chối
                notificationEvent.setSubject("Thông báo kết quả ứng tuyển");
                notificationEvent.setMsgBody("Rất tiếc, đơn ứng tuyển của bạn không được chấp nhận. Chúng tôi hy vọng có thể hợp tác với bạn trong những cơ hội sắp tới.");
                break;
            default:
                notificationEvent.setSubject("Cập nhật trạng thái đơn ứng tuyển");
                notificationEvent.setMsgBody("Đơn ứng tuyển của bạn đã được cập nhật trạng thái. Xin vui lòng kiểm tra trên website của chúng tôi.");
                break;
        }

        // Gửi notification qua Kafka
        kafkaTemplate.send("notification-updateStatus-topic", notificationEvent);

        // Chuyển đổi đối tượng Apply sang ApplyResponse
        ApplyResponse applyResponse = modelMapper.map(apply, ApplyResponse.class);
        return applyResponse;
    }

    @Override
    public List<ApplyResponse> getListAppliesByStatus(int status) {
        List<ApplyResponse> list = applyRepository.findAll()
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
    public List<ApplyResponse> getAppliesByCandidateId(Long candidateId) {
        // Gọi FeignClient để lấy danh sách CandidateCv dựa trên candidateId
        ApiResponse<List<CandidateCvResponse>> response = candidateCvClient.getCandidateCvBycandidateId(candidateId);

        // Kiểm tra mã trạng thái và lấy danh sách CandidateCv từ ApiResponse
        List<CandidateCvResponse> candidateCvs = response.getData(); // Lấy danh sách từ trường data

        // Tạo danh sách để chứa kết quả Apply
        List<ApplyResponse> applyList = new ArrayList<>();

        // Duyệt qua danh sách CandidateCv để lấy apply cho từng candidateCvId
        for (CandidateCvResponse candidateCv : candidateCvs) {
            List<Apply> applies = applyRepository.findByCandidateCvId(candidateCv.getCandidateCvId());
            // Chuyển đổi danh sách Apply sang danh sách ApplyResponse
            List<ApplyResponse> listResponse = applies.stream()
                    .map(apply -> modelMapper.map(apply, ApplyResponse.class)) // Chuyển đổi Apply thành ApplyResponse
                    .collect(Collectors.toList());
            // Thêm tất cả ApplyResponse vào applyList
            applyList.addAll(listResponse);  // Chuyển đổi rồi thêm vào applyList
        }
        return applyList;
    }

    @Override
    public List<ApplyResponse> getAppliesByJobId(Long jobId) {
        List<Apply> list = applyRepository.findAllByJobId(jobId);
        // Chuyển đổi danh sách Apply sang danh sách ApplyResponse
        List<ApplyResponse> listResponse = list.stream()
                .map(apply -> modelMapper.map(apply, ApplyResponse.class))
                .collect(Collectors.toList());

        // Trả về danh sách ApplyResponse
        return listResponse;
    }

    @Override
    public boolean hasApplyForJob(Long candidateId, Long jobId){
        // lấy tâất cả cv của ứng viên
        ApiResponse<List<CandidateCvResponse>> response = candidateCvClient.getCandidateCvBycandidateId(candidateId);
        List<CandidateCvResponse> listCv = response.getData();
        for (CandidateCvResponse candidateCvResponse: listCv){
            Optional<Apply> apply = applyRepository.findFirstByCandidateCvIdAndJobId(candidateCvResponse.getCandidateCvId(),jobId);
            // Nếu tìm thấy đơn ứng tuyển, trả về true
            if (apply.isPresent()) {
                return true;
            }
        }
        return false;
    }
    @Override
    public List<ApplyResponse> getListAppliesByEmployer(Long employerId) {
        // Gọi FeignClient để lấy danh sách CandidateCv dựa trên candidateId
        ResponseEntity<ApiResponse<List<JobResponse>>> response = jobClient.getAllJobByemployerId(employerId);
        List<JobResponse> listApiResponse = response.getBody().getData();
        // Tạo danh sách để chứa kết quả Apply
        List<ApplyResponse> applyList = new ArrayList<>();

        // Duyệt qua danh sách  để lấy apply cho từng JobId
        for (JobResponse jobResponse : listApiResponse) {
            List<Apply> applies = applyRepository.findAllByJobId(jobResponse.getJobId());
            // Chuyển đổi danh sách Apply sang danh sách ApplyResponse
            List<ApplyResponse> listResponse = applies.stream()
                    .map(apply -> modelMapper.map(apply, ApplyResponse.class)) // Chuyển đổi Apply thành ApplyResponse
                    .collect(Collectors.toList());
            // Thêm tất cả ApplyResponse vào applyList
            applyList.addAll(listResponse);  // Chuyển đổi rồi thêm vào applyList
        }
        return applyList;
    }
    @Override
    public List<ApplyResponse> getListAppliesByCandidateCv(Long candidateCvId) {
        // Lấy danh sách Apply từ repository theo candidateCvId
        List<Apply> list = applyRepository.findByCandidateCvId(candidateCvId);

        // Chuyển đổi danh sách Apply sang danh sách ApplyResponse
        List<ApplyResponse> listResponse = list.stream()
                .map(apply -> modelMapper.map(apply, ApplyResponse.class))
                .collect(Collectors.toList());

        // Trả về danh sách ApplyResponse
        return listResponse;
    }

}
