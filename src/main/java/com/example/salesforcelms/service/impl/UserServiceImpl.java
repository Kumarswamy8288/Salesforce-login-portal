package com.example.salesforcelms.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.salesforcelms.dto.auth.RegisterRequest;
import com.example.salesforcelms.entity.Role;
import com.example.salesforcelms.entity.User;
import com.example.salesforcelms.repository.RoleRepository;
import com.example.salesforcelms.repository.UserRepository;
import com.example.salesforcelms.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<User> getAllStudents() {
        return userRepository.findUsersByRole("STUDENT");
    }

    @Override
    public List<User> getAllAdmins() {
        return userRepository.findUsersByRole("ADMIN");
    }
    
    @Override
    public User registerStudent(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new RuntimeException("Username already exists");

        if (userRepository.existsByEmail(request.getEmail()))
            throw new RuntimeException("Email already exists");

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());

        Role role = roleRepository.findByRoleName("STUDENT")
            .orElseThrow(() -> new RuntimeException("STUDENT role not found"));
        
        user.setRoles(Collections.singleton(role));
        return userRepository.save(user);
    }

    @Override
    public User registerAdmin(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new RuntimeException("Username already exists");

        if (userRepository.existsByEmail(request.getEmail()))
            throw new RuntimeException("Email already exists");

        User admin = new User();
        admin.setUsername(request.getUsername());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setEmail(request.getEmail());

        Role role = roleRepository.findByRoleName("ADMIN")
            .orElseThrow(() -> new RuntimeException("ADMIN role not found"));

        admin.setRoles(Collections.singleton(role));
        return userRepository.save(admin);
    }

    @Override
    public User updateAdmin(Long id, RegisterRequest request) {
        User admin = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Admin not found"));

        admin.setUsername(request.getUsername());
        admin.setEmail(request.getEmail());

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            admin.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return userRepository.save(admin);
    }

    @Override
    public void deleteAdmin(Long id) {
        User admin = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Admin not found"));

        userRepository.delete(admin);
    }

    // ✅ Student Update Logic
    @Override
    public User updateStudent(Long id, RegisterRequest request) {
        User student = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setUsername(request.getUsername());
        student.setEmail(request.getEmail());

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            student.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return userRepository.save(student);
    }
}
