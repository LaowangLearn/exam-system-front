package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.PurchaseOrderItem;
import com.example.examsystembackend.mapper.PurchaseOrderItemMapper;
import com.example.examsystembackend.service.PurchaseOrderItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseOrderItemServiceImpl extends ServiceImpl<PurchaseOrderItemMapper, PurchaseOrderItem> implements PurchaseOrderItemService {

    @Override
    public List<PurchaseOrderItem> getByOrderId(Long orderId) {
        return baseMapper.selectByOrderId(orderId);
    }
}