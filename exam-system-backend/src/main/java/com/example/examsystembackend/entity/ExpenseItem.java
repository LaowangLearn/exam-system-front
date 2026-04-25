package com.example.examsystembackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;

@Data
@TableName("expense_item")
public class ExpenseItem {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long reimbursementId;
    private String itemName;
    private Double amount;
    private LocalDate date;
    private String description;
}