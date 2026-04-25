package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.DiscussionGroup;
import com.example.examsystembackend.mapper.DiscussionGroupMapper;
import com.example.examsystembackend.service.DiscussionGroupService;
import org.springframework.stereotype.Service;

@Service
public class DiscussionGroupServiceImpl extends ServiceImpl<DiscussionGroupMapper, DiscussionGroup> implements DiscussionGroupService {
}