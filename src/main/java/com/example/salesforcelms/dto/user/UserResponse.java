package com.example.salesforcelms.dto.user;

import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private Set<String> roles; // e.g., ADMIN, STUDENT
}
