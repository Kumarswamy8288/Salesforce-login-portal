package com.example.salesforcelms.service;

import com.example.salesforcelms.dto.auth.RegisterRequest;
import com.example.salesforcelms.entity.User;
import java.util.List;

public interface UserService {
    User getUserByUsername(String username);
    List<User> getAllStudents();

    // Student registration
    User registerStudent(RegisterRequest request);

    // ✅ Admin registration
    User registerAdmin(RegisterRequest request);
}
