package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.Product;
import com.example.examsystembackend.mapper.ProductMapper;
import com.example.examsystembackend.service.ProductService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Override
    public String generateProductCode() {
        String prefix = "PROD";
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long count = this.count();
        return prefix + date + String.format("%04d", count + 1);
    }

    @Override
    public List<Product> getByCategory(String category) {
        return baseMapper.selectByCategory(category);
    }

    @Override
    public List<Product> search(String name) {
        return baseMapper.searchByName(name);
    }
}