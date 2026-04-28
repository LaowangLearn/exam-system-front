package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.Customer;
import com.example.examsystembackend.mapper.CustomerMapper;
import com.example.examsystembackend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

    @Override
    public List<Customer> search(String name, String industry, String level, String status) {
        return baseMapper.selectByCondition(name, industry, level, status);
    }

    @Override
    public String generateCustomerCode() {
        String prefix = "CUST";
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long count = this.count();
        return prefix + date + String.format("%04d", count + 1);
    }
}