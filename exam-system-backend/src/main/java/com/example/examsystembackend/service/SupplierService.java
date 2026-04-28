package com.example.examsystembackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.examsystembackend.entity.Supplier;

public interface SupplierService extends IService<Supplier> {
    String generateSupplierCode();
}