package com.example.salesforcelms.dto.progress;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgressRequest {
    private Long moduleId;
    private String submittedLink; // Student can submit proof URL here
}
