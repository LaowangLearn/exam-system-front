package com.example.examsystembackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("customer_task")
public class CustomerTask {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long customerId;
    private String title;
    private String description;
    private String priority;
    private String status;
    private LocalDateTime dueTime;
    private Long assigneeId;
    private Long createBy;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}