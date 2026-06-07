package com.swifttrade.auth.service;

import com.swifttrade.auth.dto.request.LoginRequest;
import com.swifttrade.auth.dto.request.SignUpRequest;
import com.swifttrade.auth.dto.response.UserResponse;

import java.util.Map;

public interface AuthService {

    Map<String, String> login(LoginRequest request);

    void logout();

    UserResponse signup(SignUpRequest request);
}