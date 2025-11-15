package com.example.salesforcelms.repository;

import com.example.salesforcelms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    // ✅ Find users by role name in roles table
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.roleName = :role")
    List<User> findUsersByRole(@Param("role") String role);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
