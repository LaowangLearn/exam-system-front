package com.example.examsystembackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.examsystembackend.entity.CustomerContact;

import java.util.List;

public interface CustomerContactService extends IService<CustomerContact> {
    List<CustomerContact> getByCustomerId(Long customerId);
}