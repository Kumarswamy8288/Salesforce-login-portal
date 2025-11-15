package com.example.salesforcelms.repository;

import com.example.salesforcelms.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProgressRepository extends JpaRepository<Progress, Long> {
    List<Progress> findByUser_Id(Long userId); // fetch by user ID
    List<Progress> findByStatus(String status); // fetch by status
}
