package com.example.examsystembackend.controller;

import com.example.examsystembackend.entity.Module;
import com.example.examsystembackend.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 模块控制器
 */
@RestController
@RequestMapping("/modules")
@RequiredArgsConstructor
public class ModuleController {

    private final ModuleService moduleService;

    /**
     * 查询所有模块
     */
    @GetMapping
    public List<Module> getAllModules() {
        return moduleService.getAllModules();
    }
}