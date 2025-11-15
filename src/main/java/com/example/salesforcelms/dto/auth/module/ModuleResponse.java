package com.example.salesforcelms.dto.auth.module;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModuleResponse {
    private Long id;
    private String title;
    private String description;
    private String trailheadUrl; // Salesforce link
}
