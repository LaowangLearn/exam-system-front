package com.example.examsystembackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.examsystembackend.entity.Order;

import java.util.List;

public interface OrderService extends IService<Order> {
    String generateOrderNo();
    List<Order> getByUserId(Long userId);
}