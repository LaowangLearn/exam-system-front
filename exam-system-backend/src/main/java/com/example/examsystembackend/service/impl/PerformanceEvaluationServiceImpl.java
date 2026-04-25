package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.PerformanceEvaluation;
import com.example.examsystembackend.mapper.PerformanceEvaluationMapper;
import com.example.examsystembackend.service.PerformanceEvaluationService;
import org.springframework.stereotype.Service;

@Service
public class PerformanceEvaluationServiceImpl extends ServiceImpl<PerformanceEvaluationMapper, PerformanceEvaluation> implements PerformanceEvaluationService {
}