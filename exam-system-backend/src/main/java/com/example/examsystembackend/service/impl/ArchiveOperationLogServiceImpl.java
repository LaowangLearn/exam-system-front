package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.ArchiveOperationLog;
import com.example.examsystembackend.mapper.ArchiveOperationLogMapper;
import com.example.examsystembackend.service.ArchiveOperationLogService;
import org.springframework.stereotype.Service;

@Service
public class ArchiveOperationLogServiceImpl extends ServiceImpl<ArchiveOperationLogMapper, ArchiveOperationLog> implements ArchiveOperationLogService {
}