package com.careerstreet.user_service.service.implement;

import com.careerstreet.user_service.dto.*;
import com.careerstreet.user_service.entity.Account;
import com.careerstreet.user_service.entity.Role;
import com.careerstreet.user_service.exception.EntityExitsException;
import com.careerstreet.user_service.exception.EntityNotFoundException;
import com.careerstreet.user_service.exception.GlobalCode;
import com.careerstreet.user_service.repository.AccountRepository;

import com.careerstreet.user_service.client.CandidateClient;
import com.careerstreet.user_service.repository.RoleRepository;
import com.careerstreet.user_service.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    @Autowired
    private PasswordEncoder encoder;
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;

    private final CandidateClient candidateClient;



    protected AccountResponse convertToAccountResponse(Account account) {

        return new AccountResponse(account.getUsername(), "",null);
    }


    @Override
    public AccountResponse registerAccount(RegisterRequest registerRequest) {
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
    public Long getCandidateIdByUsername (String username){
        ApiResponse<CandidateResponse> apiResponse = candidateClient.getCandidate(username).getBody();
        if (apiResponse != null && apiResponse.getData() != null) {
            return apiResponse.getData().getCandidateId();
        }
        return null;
    }

}
