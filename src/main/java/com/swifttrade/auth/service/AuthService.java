package com.swifttrade.auth.service;

import com.swifttrade.auth.dto.request.LoginRequest;
import com.swifttrade.auth.dto.request.SignUpRequest;
import com.swifttrade.auth.dto.response.LoginResponse;
import com.swifttrade.auth.dto.response.UserResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    void logout();

    UserResponse signup(SignUpRequest request);
}