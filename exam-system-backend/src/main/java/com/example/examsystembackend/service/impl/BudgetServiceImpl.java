package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.Budget;
import com.example.examsystembackend.mapper.BudgetMapper;
import com.example.examsystembackend.service.BudgetService;
import org.springframework.stereotype.Service;

@Service
public class BudgetServiceImpl extends ServiceImpl<BudgetMapper, Budget> implements BudgetService {
}