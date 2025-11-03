package com.example.salesforcelms.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    // New column to show roles as comma-separated string
    private String roleNames;

    @PrePersist
    @PreUpdate
    public void updateRoleNames() {
        if (roles != null && !roles.isEmpty()) {
            this.roleNames = roles.stream()
                                  .map(Role::getRoleName)
                                  .collect(Collectors.joining(","));
        }
    }
}
