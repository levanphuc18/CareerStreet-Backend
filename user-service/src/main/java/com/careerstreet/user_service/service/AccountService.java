package com.careerstreet.user_service.service;

import com.careerstreet.user_service.dto.AccountRes;
import com.careerstreet.user_service.dto.AccountResponse;
import com.careerstreet.user_service.dto.RegisterRequest;
import com.careerstreet.user_service.dto.UserResponse;
import com.careerstreet.user_service.entity.Account;

import java.util.List;

public interface AccountService {
    AccountResponse registerAccount(RegisterRequest registerRequest);
    Account getAccount(String username);
    Long getRoleIdByUsername(String username);

    Long getUserIdByUsername(String username);
    String getEmailByUsername(String username);
    List<AccountRes> getAccountByRole(Long roleId);
    AccountRes updateIsActive(String username, boolean isActive);
}
