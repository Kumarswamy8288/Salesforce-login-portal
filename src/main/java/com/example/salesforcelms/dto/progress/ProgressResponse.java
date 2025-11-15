package com.example.salesforcelms.dto.progress;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProgressResponse {
    private Long id;
    private String moduleTitle;
    private String submittedLink;
    private String status;
    private LocalDateTime submittedAt;
    private LocalDateTime approvedAt;
    private String adminComment;

    // ✅ Add these for admin view
    private String studentUsername;
    private String studentEmail;
}
