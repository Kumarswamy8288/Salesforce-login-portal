package com.example.salesforcelms.config;

import com.example.salesforcelms.entity.Role;
import com.example.salesforcelms.entity.User;
import com.example.salesforcelms.repository.RoleRepository;
import com.example.salesforcelms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Ensure STUDENT role exists
        if (!roleRepository.findByRoleName("STUDENT").isPresent()) {
            Role studentRole = new Role();
            studentRole.setRoleName("STUDENT");
            roleRepository.save(studentRole);
        }

        // Ensure ADMIN role exists
        Role adminRole = roleRepository.findByRoleName("ADMIN").orElseGet(() -> {
            Role newAdminRole = new Role();
            newAdminRole.setRoleName("ADMIN");
            return roleRepository.save(newAdminRole);
        });

        // ✅ Create default super admin if not exists
        if (userRepository.findByUsername("mainadmin").isEmpty()) {
            User admin = new User();
            admin.setUsername("mainadmin");
            admin.setEmail("mainadmin@lms.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRoles(Collections.singleton(adminRole));
            userRepository.save(admin);
            System.out.println("✅ Default super admin created: username=mainadmin, password=admin123");
        }
    }
}
