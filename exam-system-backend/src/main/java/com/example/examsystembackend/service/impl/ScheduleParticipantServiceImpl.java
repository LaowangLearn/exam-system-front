package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.ScheduleParticipant;
import com.example.examsystembackend.mapper.ScheduleParticipantMapper;
import com.example.examsystembackend.service.ScheduleParticipantService;
import org.springframework.stereotype.Service;

@Service
public class ScheduleParticipantServiceImpl extends ServiceImpl<ScheduleParticipantMapper, ScheduleParticipant> implements ScheduleParticipantService {
}
