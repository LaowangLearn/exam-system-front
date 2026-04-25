package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.Salary;
import com.example.examsystembackend.mapper.SalaryMapper;
import com.example.examsystembackend.service.SalaryService;
import org.springframework.stereotype.Service;

@Service
public class SalaryServiceImpl extends ServiceImpl<SalaryMapper, Salary> implements SalaryService {
}