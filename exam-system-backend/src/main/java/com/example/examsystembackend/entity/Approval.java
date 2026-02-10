package com.example.examsystembackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("approval")
public class Approval {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long documentId;
    private String type;
    private String status;
    private Long applicantId;
    private Long approverId;
    private String content;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
