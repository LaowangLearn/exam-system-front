package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.CustomerTask;
import com.example.examsystembackend.mapper.CustomerTaskMapper;
import com.example.examsystembackend.service.CustomerTaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerTaskServiceImpl extends ServiceImpl<CustomerTaskMapper, CustomerTask> implements CustomerTaskService {

    @Override
    public List<CustomerTask> getByCustomerId(Long customerId) {
        return baseMapper.selectByCustomerId(customerId);
    }

    @Override
    public List<CustomerTask> getByAssigneeId(Long assigneeId) {
        return baseMapper.selectByAssigneeId(assigneeId);
    }
}