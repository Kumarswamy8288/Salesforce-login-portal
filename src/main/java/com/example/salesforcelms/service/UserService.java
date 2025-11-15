package com.example.salesforcelms.service;

import java.util.List;
import com.example.salesforcelms.dto.auth.RegisterRequest;
import com.example.salesforcelms.entity.User;

public interface UserService {
    
    User getUserByUsername(String username);

    List<User> getAllStudents();
    List<User> getAllAdmins();

    User registerStudent(RegisterRequest request);
    User registerAdmin(RegisterRequest request);
    User updateStudent(Long id, RegisterRequest request);


    User updateAdmin(Long id, RegisterRequest request);
    void deleteAdmin(Long id);
}
