package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.CustomerContact;
import com.example.examsystembackend.mapper.CustomerContactMapper;
import com.example.examsystembackend.service.CustomerContactService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerContactServiceImpl extends ServiceImpl<CustomerContactMapper, CustomerContact> implements CustomerContactService {

    @Override
    public List<CustomerContact> getByCustomerId(Long customerId) {
        return baseMapper.selectByCustomerId(customerId);
    }
}