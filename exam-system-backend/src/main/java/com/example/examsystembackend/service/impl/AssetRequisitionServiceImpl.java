package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.AssetRequisition;
import com.example.examsystembackend.mapper.AssetRequisitionMapper;
import com.example.examsystembackend.service.AssetRequisitionService;
import org.springframework.stereotype.Service;

@Service
public class AssetRequisitionServiceImpl extends ServiceImpl<AssetRequisitionMapper, AssetRequisition> implements AssetRequisitionService {
}