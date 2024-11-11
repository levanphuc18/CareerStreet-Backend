package com.careerstreet.user_service.service;

import com.careerstreet.user_service.dto.AccountResponse;
import com.careerstreet.user_service.dto.RegisterRequest;
import com.careerstreet.user_service.dto.UserResponse;
import com.careerstreet.user_service.entity.Account;

public interface AccountService {
    AccountResponse registerAccount(RegisterRequest registerRequest);
    Account getAccount(String username);
    Long getRoleIdByUsername(String username);

    Long getUserIdByUsername(String username);
    String getEmailByUsername(String username);
}
