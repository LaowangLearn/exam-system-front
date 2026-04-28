package com.example.examsystembackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.examsystembackend.entity.PurchaseOrderItem;

import java.util.List;

public interface PurchaseOrderItemService extends IService<PurchaseOrderItem> {
    List<PurchaseOrderItem> getByOrderId(Long orderId);
}