package com.example.salesforcelms.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private String username;  // Student or Admin username
    private String role;      // STUDENT or ADMIN
    private String message;   // e.g., "Logged in successfully"
    private String token;     // JWT token
}
