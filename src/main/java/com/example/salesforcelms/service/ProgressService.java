package com.example.salesforcelms.service;

import com.example.salesforcelms.dto.progress.*;
import com.example.salesforcelms.entity.User;
import java.util.List;

public interface ProgressService {

    // Student submits a module progress with proof URL
    ProgressResponse submitProgress(ProgressRequest request, User student);

    // List all pending progress for admin review
    List<ProgressResponse> listPendingApprovals();

    // Deprecated generic approveOrReject method
    void approveOrReject(ApproveRejectRequest request);

    // List progress for a specific student
    List<ProgressResponse> listProgressForStudent(User student);

    // ------------------ NEW METHODS ------------------

    // Admin approves a submitted progress
    ProgressResponse approveProgress(ApproveRejectRequest request);

    // Admin rejects a submitted progress
    ProgressResponse rejectProgress(ApproveRejectRequest request);
}
