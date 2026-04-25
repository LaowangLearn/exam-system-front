package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.AssetMaintenance;
import com.example.examsystembackend.mapper.AssetMaintenanceMapper;
import com.example.examsystembackend.service.AssetMaintenanceService;
import org.springframework.stereotype.Service;

@Service
public class AssetMaintenanceServiceImpl extends ServiceImpl<AssetMaintenanceMapper, AssetMaintenance> implements AssetMaintenanceService {
}