package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.RecruitmentApplication;
import com.example.examsystembackend.mapper.RecruitmentApplicationMapper;
import com.example.examsystembackend.service.RecruitmentApplicationService;
import org.springframework.stereotype.Service;

@Service
public class RecruitmentApplicationServiceImpl extends ServiceImpl<RecruitmentApplicationMapper, RecruitmentApplication> implements RecruitmentApplicationService {
}