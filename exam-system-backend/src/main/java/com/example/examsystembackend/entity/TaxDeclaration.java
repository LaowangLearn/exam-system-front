package com.example.examsystembackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("tax_declaration")
public class TaxDeclaration {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String declarationPeriod;
    private String taxType;
    private Double taxAmount;
    private LocalDate declarationDate;
    private LocalDate paymentDate;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}