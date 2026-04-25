package com.example.examsystembackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("asset")
public class Asset {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String assetNo;
    private String name;
    private Long categoryId;
    private String model;
    private String serialNumber;
    private LocalDate purchaseDate;
    private Double purchasePrice;
    private Double currentValue;
    private Integer usefulLife;
    private String status;
    private String location;
    private Long custodianId;
    private Long departmentId;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}