package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.Approval;
import com.example.examsystembackend.mapper.ApprovalMapper;
import com.example.examsystembackend.service.ApprovalService;
import org.springframework.stereotype.Service;

@Service
public class ApprovalServiceImpl extends ServiceImpl<ApprovalMapper, Approval> implements ApprovalService {
}
