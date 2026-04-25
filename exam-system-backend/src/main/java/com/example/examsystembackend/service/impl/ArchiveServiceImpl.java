package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.Archive;
import com.example.examsystembackend.mapper.ArchiveMapper;
import com.example.examsystembackend.service.ArchiveService;
import org.springframework.stereotype.Service;

@Service
public class ArchiveServiceImpl extends ServiceImpl<ArchiveMapper, Archive> implements ArchiveService {
}