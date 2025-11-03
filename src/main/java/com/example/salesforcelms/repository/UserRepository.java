package com.example.salesforcelms.repository;

import com.example.salesforcelms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    // ✅ Check if username already exists
    boolean existsByUsername(String username);

    // ✅ Check if email already exists
    boolean existsByEmail(String email);

    // ✅ Add this line for forgot password feature
    Optional<User> findByEmail(String email);
}
