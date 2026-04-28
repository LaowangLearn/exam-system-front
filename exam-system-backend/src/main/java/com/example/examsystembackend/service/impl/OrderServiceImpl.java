package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.Order;
import com.example.examsystembackend.mapper.OrderMapper;
import com.example.examsystembackend.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Override
    public String generateOrderNo() {
        String prefix = "ORD";
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long count = this.count();
        return prefix + date + String.format("%06d", count + 1);
    }

    @Override
    public List<Order> getByUserId(Long userId) {
        return baseMapper.selectByUserId(userId);
    }
}