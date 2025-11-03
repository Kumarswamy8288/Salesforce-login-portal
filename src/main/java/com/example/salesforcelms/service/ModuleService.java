package com.example.salesforcelms.service;

import com.example.salesforcelms.dto.auth.module.ModuleRequest;
import com.example.salesforcelms.dto.auth.module.ModuleResponse;
import java.util.List;

public interface ModuleService {
    ModuleResponse createModule(ModuleRequest request);
    ModuleResponse updateModule(Long id, ModuleRequest request);
    void deleteModule(Long id);
    List<ModuleResponse> getAllModules();
}
