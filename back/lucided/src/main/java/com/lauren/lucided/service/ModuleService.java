package com.lauren.lucided.service;

import com.lauren.lucided.model.Module;
import com.lauren.lucided.repository.ModuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;

    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public List<Module> getAllModules() {
        return moduleRepository.findAll();
    }

    public Optional<Module> getModuleById(Long id) {
        return moduleRepository.findById(id);
    }

    public Module createModule(Module module) {
        return moduleRepository.save(module);
    }

    public Module updateModule(Long id, Module updatedModule) {
        return moduleRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(updatedModule.getTitle());
                    existing.setCourse(updatedModule.getCourse());
                    existing.setQuizzes(updatedModule.getQuizzes());
                    return moduleRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Module not found"));
    }

    public void deleteModule(Long id) {
        moduleRepository.deleteById(id);
    }
}