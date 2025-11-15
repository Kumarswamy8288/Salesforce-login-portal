package com.example.salesforcelms.service;

import java.util.List;

import com.example.salesforcelms.dto.auth.module.ModuleRequest;
import com.example.salesforcelms.dto.auth.module.ModuleResponse;

public interface ModuleService {
    ModuleResponse createModule(ModuleRequest request);
    ModuleResponse updateModule(Long id, ModuleRequest request);
    void deleteModule(Long id);
    List<ModuleResponse> getAllModules();
}

