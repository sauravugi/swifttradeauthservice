package com.swifttrade.auth.service;

import com.swifttrade.auth.dto.request.LoginRequest;
import com.swifttrade.auth.dto.response.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    void logout();
}