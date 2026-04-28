package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.Supplier;
import com.example.examsystembackend.mapper.SupplierMapper;
import com.example.examsystembackend.service.SupplierService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, Supplier> implements SupplierService {

    @Override
    public String generateSupplierCode() {
        String prefix = "SUP";
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long count = this.count();
        return prefix + date + String.format("%04d", count + 1);
    }
}