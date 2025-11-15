package com.example.salesforcelms.service;

import com.example.salesforcelms.dto.auth.LoginRequest;
import com.example.salesforcelms.dto.auth.LoginResponse;
import com.example.salesforcelms.dto.auth.RegisterRequest;
import com.example.salesforcelms.dto.auth.ForgotPasswordRequest;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    void register(RegisterRequest request);
    void forgotPasswordByEmail(ForgotPasswordRequest request);
}
