package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.AssetScrap;
import com.example.examsystembackend.mapper.AssetScrapMapper;
import com.example.examsystembackend.service.AssetScrapService;
import org.springframework.stereotype.Service;

@Service
public class AssetScrapServiceImpl extends ServiceImpl<AssetScrapMapper, AssetScrap> implements AssetScrapService {
}