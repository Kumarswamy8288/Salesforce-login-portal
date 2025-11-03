package com.example.salesforcelms.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordRequest {
    private String email;
    private String newPassword;
}
