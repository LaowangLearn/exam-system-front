package com.example.examsystembackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("voucher_item")
public class VoucherItem {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long voucherId;
    private String accountCode;
    private String accountName;
    private Double debit;
    private Double credit;
    private String description;
}