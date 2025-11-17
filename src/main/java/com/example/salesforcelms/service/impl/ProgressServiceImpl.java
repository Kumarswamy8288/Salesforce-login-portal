package com.example.salesforcelms.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.salesforcelms.dto.progress.ApproveRejectRequest;
import com.example.salesforcelms.dto.progress.ProgressRequest;
import com.example.salesforcelms.dto.progress.ProgressResponse;
import com.example.salesforcelms.entity.Module;
import com.example.salesforcelms.entity.Progress;
import com.example.salesforcelms.entity.User;
import com.example.salesforcelms.repository.ModuleRepository;
import com.example.salesforcelms.repository.ProgressRepository;
import com.example.salesforcelms.service.ProgressService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProgressServiceImpl implements ProgressService {

    private final ProgressRepository progressRepository;
    private final ModuleRepository moduleRepository;

    @Override
    public List<ProgressResponse> getAllProgress() {
        return progressRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProgressResponse submitProgress(ProgressRequest request, User student) {
        Module module = moduleRepository.findById(request.getModuleId())
                .orElseThrow(() -> new RuntimeException("Module not found"));

        progressRepository.findByUser_Id(student.getId()).stream()
                .filter(p -> p.getModule().getId().equals(request.getModuleId()) && !p.getStatus().equals("REJECTED"))
                .findFirst()
                .ifPresent(p -> { throw new RuntimeException("Already submitted or approved"); });

        Progress progress = new Progress();
        progress.setUser(student);
        progress.setModule(module);
        progress.setSubmittedLink(request.getSubmittedLink());
        progress.setStatus("PENDING");
        progress.setSubmittedAt(LocalDateTime.now());

        Progress saved = progressRepository.save(progress);
        return mapToResponse(saved);
    }

    @Override
    public List<ProgressResponse> listPendingApprovals() {
        return progressRepository.findByStatus("PENDING")
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void approveOrReject(ApproveRejectRequest request) {}

    @Override
    public List<ProgressResponse> listProgressForStudent(User student) {
        return progressRepository.findByUser_Id(student.getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProgressResponse approveProgress(ApproveRejectRequest request) {
        Progress p = progressRepository.findById(request.getProgressId())
                .orElseThrow(() -> new RuntimeException("Not found"));
        p.setStatus("APPROVED");
        p.setAdminComment(request.getAdminComment());
        p.setApprovedAt(LocalDateTime.now());
        progressRepository.save(p);
        return mapToResponse(p);
    }

    @Override
    public ProgressResponse rejectProgress(ApproveRejectRequest request) {
        Progress p = progressRepository.findById(request.getProgressId())
                .orElseThrow(() -> new RuntimeException("Not found"));
        p.setStatus("REJECTED");
        p.setAdminComment(request.getAdminComment());
        p.setApprovedAt(null);
        progressRepository.save(p);
        return mapToResponse(p);
    }

    // ⭐ NEW METHOD
    @Override
    public List<ProgressResponse> getProgressByStudentId(Long studentId) {
        return progressRepository.findByUser_Id(studentId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ProgressResponse mapToResponse(Progress progress) {
        ProgressResponse r = new ProgressResponse();
        r.setId(progress.getId());
        r.setModuleTitle(progress.getModule().getTitle());
        r.setSubmittedLink(progress.getSubmittedLink());
        r.setStatus(progress.getStatus());
        r.setSubmittedAt(progress.getSubmittedAt());
        r.setApprovedAt(progress.getApprovedAt());
        r.setAdminComment(progress.getAdminComment());
        r.setStudentUsername(progress.getUser().getUsername());
        r.setStudentEmail(progress.getUser().getEmail());
        return r;
    }
}
