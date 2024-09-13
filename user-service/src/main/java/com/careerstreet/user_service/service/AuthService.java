package com.careerstreet.user_service.service;

import com.careerstreet.user_service.dto.TokenResponse;

public interface AuthService {
    TokenResponse createToken(String username, String password);
}
