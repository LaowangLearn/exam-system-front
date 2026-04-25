package com.example.examsystembackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("voucher")
public class Voucher {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String voucherNo;
    private LocalDate date;
    private Double totalDebit;
    private Double totalCredit;
    private String status;
    private Long creatorId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}