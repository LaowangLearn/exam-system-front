package com.example.examsystembackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("bank_transaction")
public class BankTransaction {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long accountId;
    private LocalDate transactionDate;
    private Double amount;
    private String type;
    private String description;
    private String status;
    private LocalDateTime createTime;
}