package com.example.examsystembackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("budget")
public class Budget {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Long departmentId;
    private Double amount;
    private Double usedAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}