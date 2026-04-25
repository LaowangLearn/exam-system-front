package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.GroupMessage;
import com.example.examsystembackend.mapper.GroupMessageMapper;
import com.example.examsystembackend.service.GroupMessageService;
import org.springframework.stereotype.Service;

@Service
public class GroupMessageServiceImpl extends ServiceImpl<GroupMessageMapper, GroupMessage> implements GroupMessageService {
}