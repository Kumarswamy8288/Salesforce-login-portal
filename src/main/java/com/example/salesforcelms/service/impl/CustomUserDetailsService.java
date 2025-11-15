package com.example.salesforcelms.service.impl;

import com.example.salesforcelms.entity.User;
import com.example.salesforcelms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user from DB
        User userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Convert Set<Role> to array of Strings like ["ADMIN", "STUDENT"]
        String[] rolesArray = userEntity.getRoles()
                .stream()
                .map(role -> role.getRoleName())  // use roleName, not name
                .toArray(String[]::new);

        // Build Spring Security UserDetails
        UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(userEntity.getUsername());
        builder.password(userEntity.getPassword());
        builder.roles(rolesArray); // assign roles

        return builder.build();
    }
}
