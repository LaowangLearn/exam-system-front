package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.InternalMessage;
import com.example.examsystembackend.mapper.InternalMessageMapper;
import com.example.examsystembackend.service.InternalMessageService;
import org.springframework.stereotype.Service;

@Service
public class InternalMessageServiceImpl extends ServiceImpl<InternalMessageMapper, InternalMessage> implements InternalMessageService {
}