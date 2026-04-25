package com.example.examsystembackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("asset_scrap")
public class AssetScrap {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long assetId;
    private LocalDate scrapDate;
    private String scrapReason;
    private String scrapType;
    private Double residualValue;
    private String approvalStatus;
    private Long approverId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}