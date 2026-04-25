package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.ArchiveCategory;
import com.example.examsystembackend.mapper.ArchiveCategoryMapper;
import com.example.examsystembackend.service.ArchiveCategoryService;
import org.springframework.stereotype.Service;

@Service
public class ArchiveCategoryServiceImpl extends ServiceImpl<ArchiveCategoryMapper, ArchiveCategory> implements ArchiveCategoryService {
}