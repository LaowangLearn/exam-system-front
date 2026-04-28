package com.example.examsystembackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.examsystembackend.entity.Product;

import java.util.List;

public interface ProductService extends IService<Product> {
    String generateProductCode();
    List<Product> getByCategory(String category);
    List<Product> search(String name);
}