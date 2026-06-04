package com.swifttrade.auth.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserResponse {

    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    private Set<String> roles;

    private Boolean active;

    private String clientId;

    private String clientName;

    private Set<String> departments;
}