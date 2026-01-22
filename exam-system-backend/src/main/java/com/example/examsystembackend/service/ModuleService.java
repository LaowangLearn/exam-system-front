package com.example.examsystembackend.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.Module;
import com.example.examsystembackend.mapper.ModuleMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleService extends ServiceImpl<ModuleMapper, Module> {

    public List<Module> getAllModules() {
        return this.list();
    }
}