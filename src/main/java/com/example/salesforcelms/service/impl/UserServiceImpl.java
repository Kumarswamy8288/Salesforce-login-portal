package com.example.salesforcelms.service.impl;

import com.example.salesforcelms.dto.auth.RegisterRequest;
import com.example.salesforcelms.entity.Role;
import com.example.salesforcelms.entity.User;
import com.example.salesforcelms.repository.RoleRepository;
import com.example.salesforcelms.repository.UserRepository;
import com.example.salesforcelms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

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
        return userRepository.findAll().stream()
                .filter(u -> u.getRoles().stream()
                        .anyMatch(r -> r.getRoleName().equals("STUDENT")))
                .toList();
    }

    @Override
    public User registerStudent(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword())); 
        user.setEmail(request.getEmail());

        Role studentRole = roleRepository.findByRoleName("STUDENT")
                .orElseThrow(() -> new RuntimeException("STUDENT role not found"));
        user.setRoles(Collections.singleton(studentRole));

        return userRepository.save(user);
    }

    // ✅ New method: Admin registration
    @Override
    public User registerAdmin(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User admin = new User();
        admin.setUsername(request.getUsername());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setEmail(request.getEmail());

        Role adminRole = roleRepository.findByRoleName("ADMIN")
                .orElseThrow(() -> new RuntimeException("ADMIN role not found"));
        admin.setRoles(Collections.singleton(adminRole));

        return userRepository.save(admin);
    }
}
