package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.PurchaseOrder;
import com.example.examsystembackend.mapper.PurchaseOrderMapper;
import com.example.examsystembackend.service.PurchaseOrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PurchaseOrderServiceImpl extends ServiceImpl<PurchaseOrderMapper, PurchaseOrder> implements PurchaseOrderService {

    @Override
    public String generateOrderCode() {
        String prefix = "PO";
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long count = this.count();
        return prefix + date + String.format("%04d", count + 1);
    }

    @Override
    public List<PurchaseOrder> getBySupplierId(Long supplierId) {
        return baseMapper.selectBySupplierId(supplierId);
    }
}