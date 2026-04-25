package com.example.examsystembackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("expense_reimbursement")
public class ExpenseReimbursement {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long departmentId;
    private Double amount;
    private String purpose;
    private String status;
    private LocalDate submitDate;
    private LocalDate approvalDate;
    private LocalDate paymentDate;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}