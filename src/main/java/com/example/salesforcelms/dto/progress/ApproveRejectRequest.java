package com.example.salesforcelms.dto.progress;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApproveRejectRequest {
    private Long progressId;
    private String action; // APPROVE or REJECT
    private String adminComment;
}
