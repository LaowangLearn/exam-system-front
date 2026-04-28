package com.example.examsystembackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("supplier")
public class Supplier {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String supplierCode;
    private String name;
    private String contactName;
    private String phone;
    private String email;
    private String address;
    private String bankName;
    private String bankAccount;
    private String taxNumber;
    private String businessLicense;
    private String rating;
    private String status;
    private BigDecimal creditLimit;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}