package com.example.examsystembackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("customer_contact")
public class CustomerContact {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long customerId;
    private String contactType;
    private String content;
    private String result;
    private String nextAction;
    private Long createBy;
    private LocalDateTime createTime;
}