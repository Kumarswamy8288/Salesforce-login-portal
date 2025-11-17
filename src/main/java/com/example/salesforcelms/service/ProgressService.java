package com.example.salesforcelms.service;

import java.util.List;

import com.example.salesforcelms.dto.progress.ApproveRejectRequest;
import com.example.salesforcelms.dto.progress.ProgressRequest;
import com.example.salesforcelms.dto.progress.ProgressResponse;
import com.example.salesforcelms.entity.User;

public interface ProgressService {

    List<ProgressResponse> getAllProgress();

    ProgressResponse submitProgress(ProgressRequest request, User student);

    List<ProgressResponse> listPendingApprovals();

    void approveOrReject(ApproveRejectRequest request); // deprecated

    List<ProgressResponse> listProgressForStudent(User student);

    ProgressResponse approveProgress(ApproveRejectRequest request);

    ProgressResponse rejectProgress(ApproveRejectRequest request);

    // ⭐ NEW METHOD
    List<ProgressResponse> getProgressByStudentId(Long studentId);
}
