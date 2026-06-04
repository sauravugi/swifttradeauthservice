package com.swifttrade.auth.service.impl;

import com.swifttrade.auth.configuration.JwtConfig;
import com.swifttrade.auth.dto.request.LoginRequest;
import com.swifttrade.auth.dto.request.SignUpRequest;
import com.swifttrade.auth.dto.response.LoginResponse;
import com.swifttrade.auth.dto.response.UserResponse;
import com.swifttrade.auth.exception.InvalidCredentialsException;
import com.swifttrade.auth.model.Department;
import com.swifttrade.auth.model.User;
import com.swifttrade.auth.repository.UserRepository;
import com.swifttrade.auth.security.JwtService;
import com.swifttrade.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final JwtConfig jwtConfig;
    private final PasswordEncoder passwordEncoder;

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
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .username(user.getUserName())
                .roles(user.getRoles()
                        .stream()
                        .map(Enum::name)
                        .collect(java.util.stream.Collectors.toSet()))
                .active(user.getActive())
                .clientId(
                        user.getClient() != null
                                ? user.getClient().getId()
                                : null
                )
                .clientName(
                        user.getClient() != null
                                ? user.getClient().getName()
                                : null
                )
                .departments(
                        user.getDepartments() != null
                                ? user.getDepartments()
                                .stream()
                                .map(Department::getName)
                                .collect(Collectors.toSet())
                                : Set.of()
                )
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

    @Override
    public UserResponse signup(SignUpRequest request) {

        if (userRepository.findByUserName(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setUserName(request.getUsername());
        user.setEmployeeId(request.getEmployeeId());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setActive(
                request.getActive() != null
                        ? request.getActive()
                        : true
        );

        user.setRoles(request.getRoles());

        User savedUser = userRepository.save(user);

        return UserResponse.builder()
                .id(savedUser.getId())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .email(savedUser.getEmail())
                .username(savedUser.getUserName())
                .active(savedUser.getActive())
                .roles(
                        savedUser.getRoles()
                                .stream()
                                .map(Enum::name)
                                .collect(java.util.stream.Collectors.toSet())
                )
                .build();
    }
}