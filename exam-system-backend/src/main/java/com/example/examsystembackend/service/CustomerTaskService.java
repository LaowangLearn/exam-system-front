package com.example.examsystembackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.examsystembackend.entity.CustomerTask;

import java.util.List;

public interface CustomerTaskService extends IService<CustomerTask> {
    List<CustomerTask> getByCustomerId(Long customerId);
    List<CustomerTask> getByAssigneeId(Long assigneeId);
}