package com.example.salesforcelms.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleResponse {
    private Long id;
    private String roleName; // ADMIN or STUDENT
}
