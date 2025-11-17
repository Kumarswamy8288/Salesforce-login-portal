package com.example.salesforcelms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.salesforcelms.dto.auth.RegisterRequest;
import com.example.salesforcelms.dto.auth.module.ModuleRequest;
import com.example.salesforcelms.dto.auth.module.ModuleResponse;
import com.example.salesforcelms.dto.progress.ApproveRejectRequest;
import com.example.salesforcelms.dto.progress.ProgressResponse;
import com.example.salesforcelms.entity.User;
import com.example.salesforcelms.service.ModuleService;
import com.example.salesforcelms.service.ProgressService;
import com.example.salesforcelms.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ModuleService moduleService;
    private final ProgressService progressService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerAdmin(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.registerAdmin(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admins")
    public ResponseEntity<List<User>> getAllAdmins() {
        return ResponseEntity.ok(userService.getAllAdmins());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/students")
    public ResponseEntity<List<User>> getAllStudents() {
        return ResponseEntity.ok(userService.getAllStudents());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admins/{id}")
    public ResponseEntity<User> updateAdmin(@PathVariable Long id, @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.updateAdmin(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admins/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Long id) {
        userService.deleteAdmin(id);
        return ResponseEntity.ok("Admin deleted successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/modules")
    public ResponseEntity<List<ModuleResponse>> getAllModules() {
        return ResponseEntity.ok(moduleService.getAllModules());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/modules")
    public ResponseEntity<ModuleResponse> createModule(@RequestBody ModuleRequest request) {
        return ResponseEntity.ok(moduleService.createModule(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/modules/{id}")
    public ResponseEntity<ModuleResponse> updateModule(@PathVariable Long id, @RequestBody ModuleRequest request) {
        return ResponseEntity.ok(moduleService.updateModule(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/modules/{id}")
    public ResponseEntity<String> deleteModule(@PathVariable Long id) {
        moduleService.deleteModule(id);
        return ResponseEntity.ok("Module deleted successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/progress/all")
    public ResponseEntity<List<ProgressResponse>> getAllProgress() {
        return ResponseEntity.ok(progressService.getAllProgress());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/progress/pending")
    public ResponseEntity<List<ProgressResponse>> listPendingApprovals() {
        return ResponseEntity.ok(progressService.listPendingApprovals());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/progress/approve")
    public ResponseEntity<ProgressResponse> approveProgress(@RequestBody ApproveRejectRequest request) {
        return ResponseEntity.ok(progressService.approveProgress(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/progress/reject")
    public ResponseEntity<ProgressResponse> rejectProgress(@RequestBody ApproveRejectRequest request) {
        return ResponseEntity.ok(progressService.rejectProgress(request));
    }

    // ⭐ NEW API — Get progress of a single student by ID
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/progress/student/{studentId}")
    public ResponseEntity<List<ProgressResponse>> getProgressByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(progressService.getProgressByStudentId(studentId));
    }
}
