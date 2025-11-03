package com.example.salesforcelms.controller;

import com.example.salesforcelms.dto.auth.*;
import com.example.salesforcelms.service.AuthService;
import com.example.salesforcelms.service.JwtBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtBlacklistService jwtBlacklistService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            jwtBlacklistService.blacklistToken(token);
            return ResponseEntity.ok("Logout successful");
        } else {
            return ResponseEntity.badRequest().body("Invalid or missing token");
        }
    }

    // ✅ New Forgot Password Endpoint (via Email)
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        authService.forgotPasswordByEmail(request);
        return ResponseEntity.ok("Password reset successful");
    }
}
