package com.example.salesforcelms.service.impl;

import com.example.salesforcelms.dto.auth.module.ModuleRequest;
import com.example.salesforcelms.dto.auth.module.ModuleResponse;
import com.example.salesforcelms.entity.Module;
import com.example.salesforcelms.repository.ModuleRepository;
import com.example.salesforcelms.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;

    @Override
    public ModuleResponse createModule(ModuleRequest request) {
        Module module = new Module();
        module.setTitle(request.getTitle());
        module.setDescription(request.getDescription());
        module.setTrailheadUrl(request.getTrailheadUrl());

        Module saved = moduleRepository.save(module);
        return mapToResponse(saved);
    }

    @Override
    public ModuleResponse updateModule(Long id, ModuleRequest request) {
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Module not found"));

        module.setTitle(request.getTitle());
        module.setDescription(request.getDescription());
        module.setTrailheadUrl(request.getTrailheadUrl());

        Module updated = moduleRepository.save(module);
        return mapToResponse(updated);
    }

    @Override
    public void deleteModule(Long id) {
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Module not found"));
        moduleRepository.delete(module);
    }

    @Override
    public List<ModuleResponse> getAllModules() {
        return moduleRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ModuleResponse mapToResponse(Module module) {
        ModuleResponse response = new ModuleResponse();
        response.setId(module.getId());
        response.setTitle(module.getTitle());
        response.setDescription(module.getDescription());
        response.setTrailheadUrl(module.getTrailheadUrl());
        return response;
    }
}
