package com.example.salesforcelms.service.impl;

import com.example.salesforcelms.config.JwtTokenUtil;
import com.example.salesforcelms.dto.auth.LoginRequest;
import com.example.salesforcelms.dto.auth.LoginResponse;
import com.example.salesforcelms.dto.auth.RegisterRequest;
import com.example.salesforcelms.dto.auth.ForgotPasswordRequest;
import com.example.salesforcelms.entity.User;
import com.example.salesforcelms.repository.UserRepository;
import com.example.salesforcelms.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;  // ✅ Add this

    @Override
    public LoginResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username or password");
        }

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtTokenUtil.generateToken(user.getUsername());

        String role = user.getRoles().stream()
                .findFirst()
                .map(r -> r.getRoleName())
                .orElse("UNKNOWN");

        String message = "Logged in successfully";

        return new LoginResponse(user.getUsername(), role, message, token);
    }

    @Override
    public void register(RegisterRequest request) {
        // Registration handled elsewhere
    }

    @Override
    public void forgotPasswordByEmail(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + request.getEmail()));

        // ✅ Encode new password properly
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);
    }
}
