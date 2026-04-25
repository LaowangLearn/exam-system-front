package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.Asset;
import com.example.examsystembackend.mapper.AssetMapper;
import com.example.examsystembackend.service.AssetService;
import org.springframework.stereotype.Service;

@Service
public class AssetServiceImpl extends ServiceImpl<AssetMapper, Asset> implements AssetService {
}