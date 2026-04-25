package com.example.examsystembackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("asset_maintenance")
public class AssetMaintenance {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long assetId;
    private LocalDate maintenanceDate;
    private String maintenanceType;
    private String description;
    private String result;
    private Double cost;
    private String maintenanceStaff;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}