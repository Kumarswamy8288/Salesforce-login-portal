package com.example.salesforcelms.controller;

import com.example.salesforcelms.dto.auth.RegisterRequest;
import com.example.salesforcelms.dto.auth.module.ModuleRequest;
import com.example.salesforcelms.dto.auth.module.ModuleResponse;
import com.example.salesforcelms.dto.progress.ProgressResponse;
import com.example.salesforcelms.dto.progress.ApproveRejectRequest;
import com.example.salesforcelms.dto.user.UserResponse;
import com.example.salesforcelms.entity.User;
import com.example.salesforcelms.service.ModuleService;
import com.example.salesforcelms.service.ProgressService;
import com.example.salesforcelms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ModuleService moduleService;
    private final ProgressService progressService;
    private final UserService userService;

    // ---------------- ADMIN REGISTRATION ----------------
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerAdmin(@RequestBody RegisterRequest request, Authentication authentication) {
        // Ensure the logged-in user is ADMIN
        String loggedInUsername = authentication.getName();
        User loggedInUser = userService.getUserByUsername(loggedInUsername);

        boolean isAdmin = loggedInUser.getRoles().stream()
                .anyMatch(r -> r.getRoleName().equals("ADMIN"));

        if (!isAdmin) {
            throw new RuntimeException("Access Denied: Only admins can register new admins.");
        }

        User admin = userService.registerAdmin(request);
        String roleName = admin.getRoles().iterator().next().getRoleName();

        Map<String, Object> response = new HashMap<>();
        response.put("username", admin.getUsername());
        response.put("role", roleName);
        response.put("message", "New admin registered successfully by " + loggedInUsername);

        return ResponseEntity.ok(response);
    }

    // ---------------- MODULE MANAGEMENT ----------------
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/modules")
    public ResponseEntity<ModuleResponse> createModule(@RequestBody ModuleRequest request) {
        return ResponseEntity.ok(moduleService.createModule(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/modules/{id}")
    public ResponseEntity<ModuleResponse> updateModule(@PathVariable Long id,
                                                       @RequestBody ModuleRequest request) {
        return ResponseEntity.ok(moduleService.updateModule(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/modules/{id}")
    public ResponseEntity<String> deleteModule(@PathVariable Long id) {
        moduleService.deleteModule(id);
        return ResponseEntity.ok("Module deleted successfully");
    }

    // ---------------- STUDENT MANAGEMENT ----------------
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/students")
    public ResponseEntity<List<UserResponse>> getAllStudents() {
        List<UserResponse> students = userService.getAllStudents().stream().map(u -> {
            UserResponse ur = new UserResponse();
            ur.setId(u.getId());
            ur.setUsername(u.getUsername());
            ur.setEmail(u.getEmail());
            ur.setRoles(u.getRoles().stream().map(r -> r.getRoleName()).collect(Collectors.toSet()));
            return ur;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(students);
    }

    // ---------------- PROGRESS MANAGEMENT ----------------
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/progress/pending")
    public ResponseEntity<List<ProgressResponse>> getPendingProgress() {
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
}
