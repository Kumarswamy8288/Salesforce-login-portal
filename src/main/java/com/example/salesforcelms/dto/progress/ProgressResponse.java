package com.example.salesforcelms.dto.progress;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProgressResponse {
    private Long id;
    private String moduleTitle;
    private String submittedLink; // Admin/student can see the submitted proof URL
    private String status;
    private LocalDateTime submittedAt;
    private LocalDateTime approvedAt;
    private String adminComment;
}
