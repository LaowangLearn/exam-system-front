package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.AssetCategory;
import com.example.examsystembackend.mapper.AssetCategoryMapper;
import com.example.examsystembackend.service.AssetCategoryService;
import org.springframework.stereotype.Service;

@Service
public class AssetCategoryServiceImpl extends ServiceImpl<AssetCategoryMapper, AssetCategory> implements AssetCategoryService {
}