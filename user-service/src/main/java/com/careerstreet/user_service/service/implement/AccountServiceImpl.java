package com.careerstreet.user_service.service.implement;

//import com.careerstreet.user_service.client.EmployerClient;
import com.careerstreet.event.NotificationEvent;
import com.careerstreet.user_service.client.AdminClient;
import com.careerstreet.user_service.client.EmployerClient;
import com.careerstreet.user_service.client.NotificationClient;
import com.careerstreet.user_service.dto.*;
import com.careerstreet.user_service.entity.Account;
import com.careerstreet.user_service.entity.Role;
import com.careerstreet.user_service.exception.EntityExitsException;
import com.careerstreet.user_service.exception.EntityNotFoundException;
import com.careerstreet.user_service.exception.GlobalCode;
import com.careerstreet.user_service.exception.GlobalException;
import com.careerstreet.user_service.repository.AccountRepository;

import com.careerstreet.user_service.client.CandidateClient;
import com.careerstreet.user_service.repository.RoleRepository;
import com.careerstreet.user_service.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder encoder;
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;

    private final CandidateClient candidateClient;
    private final EmployerClient employerClient;
    private final AdminClient adminClient;
    private final NotificationClient notificationClient;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    protected AccountResponse convertToAccountResponse(Account account) {

        return new AccountResponse(account.getUsername(), "",null);
    }


    @Override
    public AccountResponse registerAccount(RegisterRequest registerRequest) {
        // set các giá trị notificationRequest
        NotificationEvent notificationEvent = new NotificationEvent();
        notificationEvent.setRecipient(registerRequest.getEmail());
        notificationEvent.setSubject("Xác nhận tạo tài khoản");
        notificationEvent.setMsgBody("Chúc mừng bạn đã tạo tài khoản thành công trên website tuyển dụng việc làm CarrerStreet");

        boolean checkUsername = accountRepository.existsByUsername(registerRequest.getUsername());
        Role role = roleRepository.findById(registerRequest.getRole()).orElseThrow(
                () -> new EntityNotFoundException("Chức vụ không tồn tại", GlobalCode.ERROR_ENTITY_NOT_FOUND));
        if (checkUsername) {
            throw new EntityExitsException("Tài khoản đã đăng kí trước đó", GlobalCode.ERROR_NAME_EXIST);
        }
        Account account = new Account();
        account.setUsername(registerRequest.getUsername());
        account.setPassword(encoder.encode(registerRequest.getPassword()));
        //account.setPassword(registerRequest.getPassword());
        account.setRole(role);
        account.setEmail(registerRequest.getEmail());
        account.setActive(true);
        account = accountRepository.save(account);

        // gọi api client gửi mail
//        ApiResponse<NotificationResponse> apiResponse = notificationClient.createNotification(notificationRequest).getBody();
        kafkaTemplate.send("notification-register-topic",notificationEvent);
        return convertToAccountResponse(account);
    }

    @Override
    public Account getAccount(String username) {
        Account user = accountRepository.findById(username).orElseThrow(
                () -> new EntityNotFoundException("Không tìm thấy tài khoản", GlobalCode.ERROR_ENTITY_NOT_FOUND));
        if (!user.isActive()) {
            throw new EntityNotFoundException("Tài khoản bị khóa", GlobalCode.ERROR_ENTITY_NOT_FOUND);
        }
        return user;
    }

    @Override
    public Long getRoleIdByUsername(String username) {
        System.out.println(accountRepository.findRoleIdByUsername(username) + "phuc");
        return accountRepository.findRoleIdByUsername(username);
    }

    @Override
    public Long getUserIdByUsername(String username) {
        Long roleId = this.getRoleIdByUsername(username);
        if (roleId == null) {
            throw new EntityNotFoundException("Không có role", GlobalCode.ERROR_ENTITY_NOT_FOUND);
        } else if (roleId == 3) {
            ApiResponse<CandidateResponse> apiResponse = candidateClient.getCandidate(username).getBody();
            if (apiResponse != null && apiResponse.getData() != null) {
                return apiResponse.getData().getCandidateId();
            }
        } else if (roleId == 1) {
            // Xử lý cho roleId == 1, ví dụ gọi một API hoặc truy vấn cơ sở dữ liệu để lấy userId
            ApiResponse<AdminResponse> apiResponse = adminClient.getAdmin(username).getBody();
            if (apiResponse != null && apiResponse.getData() != null) {
                return apiResponse.getData().getAdminId();
            }
        } else {
            ApiResponse<EmployerResponse> apiResponse = employerClient.getEmployer(username).getBody();
            if (apiResponse != null && apiResponse.getData() != null) {
                return apiResponse.getData().getEmployerId();
            }
        }
        return null;
    }


    @Override
    public String getEmailByUsername(String username){
        String email = accountRepository.findEmailByUsername(username);
        return email;
    }

    @Override
    public List<AccountRes> getAccountByRole(Long roleId) {
        List<AccountRes> list = accountRepository.findAll()
                .stream()
                .filter(account -> account.getRole().getRoleId() == roleId)
                .map(account -> {
                    AccountRes accountRes = modelMapper.map(account, AccountRes.class);
                    return accountRes;
                })
                .collect(Collectors.toList());
        return list;
    }
    @Override
    public AccountRes updateIsActive(String username, boolean isActive) {
        Account account = accountRepository.findAccountByUsername(username);
        // Cập nhật trạng thái ứng tuyển
        account.setActive(isActive);

        // Lưu lại thay đổi trạng thái vào cơ sở dữ liệu
        account = accountRepository.save(account);

        // Chuyển đổi đối tượng Apply sang ApplyResponse
        AccountRes accountRes = modelMapper.map(account, AccountRes.class);
        return accountRes;
    }
}
