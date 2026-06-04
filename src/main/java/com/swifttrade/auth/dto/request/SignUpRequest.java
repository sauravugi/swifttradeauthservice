package com.swifttrade.auth.dto.request;

import com.swifttrade.auth.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    private String employeeId;

    private Boolean active = true;

    private String clientId;

    @NotEmpty(message = "At least one role is required")
    private Set<User.Role> roles;

    private Set<String> departments;
}