package com.swifttrade.auth.service.impl;

import com.swifttrade.auth.configuration.JwtConfig;
import com.swifttrade.auth.dto.request.LoginRequest;
import com.swifttrade.auth.dto.response.LoginResponse;
import com.swifttrade.auth.dto.response.UserResponse;
import com.swifttrade.auth.exception.InvalidCredentialsException;
import com.swifttrade.auth.model.User;
import com.swifttrade.auth.repository.UserRepository;
import com.swifttrade.auth.security.JwtService;
import com.swifttrade.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final JwtConfig jwtConfig;

    @Override
    public LoginResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        String accessToken = jwtService.generateToken(request.getUsername());
        User user = userRepository.findByUserName(request.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));
        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .username(user.getUserName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken("")
                .tokenType("Bearer")
                .expiresIn(jwtConfig.getExpiration())
                .user(userResponse)
                .build();
    }

    @Override
    public void logout() {}
}