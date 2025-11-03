package com.example.salesforcelms.service.impl;

import com.example.salesforcelms.dto.progress.*;
import com.example.salesforcelms.entity.Module;
import com.example.salesforcelms.entity.Progress;
import com.example.salesforcelms.entity.User;
import com.example.salesforcelms.repository.ModuleRepository;
import com.example.salesforcelms.repository.ProgressRepository;
import com.example.salesforcelms.service.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgressServiceImpl implements ProgressService {

    private final ProgressRepository progressRepository;
    private final ModuleRepository moduleRepository;

    @Override
    public ProgressResponse submitProgress(ProgressRequest request, User student) {
        Module module = moduleRepository.findById(request.getModuleId())
                .orElseThrow(() -> new RuntimeException("Module not found"));

        // Check if student already submitted this module and it's pending/approved
        progressRepository.findByUser_Id(student.getId()).stream()
                .filter(p -> p.getModule().getId().equals(request.getModuleId()) && !p.getStatus().equals("REJECTED"))
                .findFirst()
                .ifPresent(p -> { throw new RuntimeException("You have already submitted this module. Wait for admin review or rejection to resubmit."); });

        Progress progress = new Progress();
        progress.setUser(student);
        progress.setModule(module);
        progress.setSubmittedLink(request.getSubmittedLink()); // proof URL
        progress.setStatus("PENDING");
        progress.setSubmittedAt(LocalDateTime.now());

        Progress saved = progressRepository.save(progress);
        return mapToResponse(saved);
    }

    @Override
    public List<ProgressResponse> listPendingApprovals() {
        return progressRepository.findByStatus("PENDING").stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void approveOrReject(ApproveRejectRequest request) {
        throw new UnsupportedOperationException("Use separate approveProgress or rejectProgress methods.");
    }

    @Override
    public List<ProgressResponse> listProgressForStudent(User student) {
        return progressRepository.findByUser_Id(student.getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ------------------ APPROVE ------------------
    @Override
    public ProgressResponse approveProgress(ApproveRejectRequest request) {
        Progress progress = progressRepository.findById(request.getProgressId())
                .orElseThrow(() -> new RuntimeException("Progress not found"));
        progress.setStatus("APPROVED");
        progress.setAdminComment(request.getAdminComment());
        progress.setApprovedAt(LocalDateTime.now());
        progressRepository.save(progress);
        return mapToResponse(progress);
    }

    // ------------------ REJECT ------------------
    @Override
    public ProgressResponse rejectProgress(ApproveRejectRequest request) {
        Progress progress = progressRepository.findById(request.getProgressId())
                .orElseThrow(() -> new RuntimeException("Progress not found"));
        progress.setStatus("REJECTED");
        progress.setAdminComment(request.getAdminComment());
        progress.setApprovedAt(null);
        progressRepository.save(progress);
        return mapToResponse(progress);
    }

    // ------------------ MAP TO RESPONSE ------------------
    private ProgressResponse mapToResponse(Progress progress) {
        ProgressResponse response = new ProgressResponse();
        response.setId(progress.getId());
        response.setModuleTitle(progress.getModule().getTitle());
        response.setSubmittedLink(progress.getSubmittedLink());
        response.setStatus(progress.getStatus());
        response.setSubmittedAt(progress.getSubmittedAt());
        response.setApprovedAt(progress.getApprovedAt());
        response.setAdminComment(progress.getAdminComment());
        return response;
    }
}
