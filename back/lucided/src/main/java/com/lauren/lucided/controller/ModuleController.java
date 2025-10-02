package com.lauren.lucided.controller;

import com.lauren.lucided.model.Module;
import com.lauren.lucided.service.ModuleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @GetMapping
    public List<Module> getAllModules() {
        return moduleService.getAllModules();
    }

    @GetMapping("/{id}")
    public Module getModuleById(@PathVariable Long id) {
        return moduleService.getModuleById(id).orElseThrow();
    }

    @PostMapping
    public Module createModule(@RequestBody Module module) {
        return moduleService.createModule(module);
    }

    @PutMapping("/{id}")
    public Module updateModule(@PathVariable Long id, @RequestBody Module module) {
        return moduleService.updateModule(id, module);
    }

    @DeleteMapping("/{id}")
    public void deleteModule(@PathVariable Long id) {
        moduleService.deleteModule(id);
    }
}