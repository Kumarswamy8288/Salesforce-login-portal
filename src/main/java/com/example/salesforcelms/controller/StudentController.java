package com.example.salesforcelms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.salesforcelms.dto.auth.RegisterRequest;
import com.example.salesforcelms.dto.auth.module.ModuleResponse;
import com.example.salesforcelms.dto.progress.ProgressRequest;
import com.example.salesforcelms.dto.progress.ProgressResponse;
import com.example.salesforcelms.entity.User;
import com.example.salesforcelms.service.ModuleService;
import com.example.salesforcelms.service.ProgressService;
import com.example.salesforcelms.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final ModuleService moduleService;
    private final ProgressService progressService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerStudent(@RequestBody RegisterRequest request) {
        User student = userService.registerStudent(request);
        String roleName = student.getRoles().iterator().next().getRoleName();

        Map<String, Object> response = new HashMap<>();
        response.put("username", student.getUsername());
        response.put("role", roleName);
        response.put("message", "Registered as a student");

        return ResponseEntity.ok(response);
    }
    @PutMapping("/students/{id}")
public ResponseEntity<User> updateStudent(@PathVariable Long id,
                                          @RequestBody RegisterRequest request) {
    return ResponseEntity.ok(userService.updateStudent(id, request));
}


    @GetMapping("/modules")
    public ResponseEntity<List<ModuleResponse>> getModules() {
        return ResponseEntity.ok(moduleService.getAllModules());
    }

    @PostMapping("/progress")
    public ResponseEntity<ProgressResponse> submitProgress(@RequestBody ProgressRequest request,
                                                           Authentication auth) {
        User student = userService.getUserByUsername(auth.getName());
        return ResponseEntity.ok(progressService.submitProgress(request, student));
    }

    @GetMapping("/progress")
    public ResponseEntity<List<ProgressResponse>> getMyProgress(Authentication auth) {
        User student = userService.getUserByUsername(auth.getName());
        return ResponseEntity.ok(progressService.listProgressForStudent(student));
    }

    // ✅ New API: Get Salesforce Trailhead link
    @GetMapping("/trailhead-link")
    public ResponseEntity<Map<String, String>> getTrailheadLink() {
        Map<String, String> response = new HashMap<>();
        response.put("trailheadHome", "https://trailhead.salesforce.com/");
        response.put("message", "Click this link to access Salesforce Trailhead Home Page.");
        return ResponseEntity.ok(response);
    }
}
