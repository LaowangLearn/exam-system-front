package com.example.examsystembackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.examsystembackend.entity.PurchaseOrder;

import java.util.List;

public interface PurchaseOrderService extends IService<PurchaseOrder> {
    String generateOrderCode();
    List<PurchaseOrder> getBySupplierId(Long supplierId);
}