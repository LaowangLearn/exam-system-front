package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.ArchiveBorrow;
import com.example.examsystembackend.mapper.ArchiveBorrowMapper;
import com.example.examsystembackend.service.ArchiveBorrowService;
import org.springframework.stereotype.Service;

@Service
public class ArchiveBorrowServiceImpl extends ServiceImpl<ArchiveBorrowMapper, ArchiveBorrow> implements ArchiveBorrowService {
}