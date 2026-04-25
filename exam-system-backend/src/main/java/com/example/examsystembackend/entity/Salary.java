package com.example.examsystembackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("salary")
public class Salary {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Double baseSalary;
    private Double bonus;
    private Double allowance;
    private Double deduction;
    private Double totalSalary;
    private String period;
    private LocalDate paymentDate;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}