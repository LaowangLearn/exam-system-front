package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.OrderItem;
import com.example.examsystembackend.mapper.OrderItemMapper;
import com.example.examsystembackend.service.OrderItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

    @Override
    public List<OrderItem> getByOrderId(Long orderId) {
        return baseMapper.selectByOrderId(orderId);
    }
}