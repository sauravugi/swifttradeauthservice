package com.swifttrade.auth.controller;

import com.swifttrade.auth.dto.request.LoginRequest;
import com.swifttrade.auth.dto.response.LoginResponse;
import com.swifttrade.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {

        authService.logout();

        return ResponseEntity.ok("Logout successful");
    }
}