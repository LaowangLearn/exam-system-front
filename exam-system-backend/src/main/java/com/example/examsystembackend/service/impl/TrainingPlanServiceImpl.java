package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.TrainingPlan;
import com.example.examsystembackend.mapper.TrainingPlanMapper;
import com.example.examsystembackend.service.TrainingPlanService;
import org.springframework.stereotype.Service;

@Service
public class TrainingPlanServiceImpl extends ServiceImpl<TrainingPlanMapper, TrainingPlan> implements TrainingPlanService {
}