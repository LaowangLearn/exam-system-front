package com.example.examsystembackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.examsystembackend.entity.OrderItem;

import java.util.List;

public interface OrderItemService extends IService<OrderItem> {
    List<OrderItem> getByOrderId(Long orderId);
}