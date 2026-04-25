package com.example.examsystembackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("bank_account")
public class BankAccount {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String accountName;
    private String accountNumber;
    private String bankName;
    private Double balance;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}