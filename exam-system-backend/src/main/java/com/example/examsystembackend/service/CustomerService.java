package com.example.examsystembackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.examsystembackend.entity.Customer;

import java.util.List;

public interface CustomerService extends IService<Customer> {
    List<Customer> search(String name, String industry, String level, String status);
    String generateCustomerCode();
}