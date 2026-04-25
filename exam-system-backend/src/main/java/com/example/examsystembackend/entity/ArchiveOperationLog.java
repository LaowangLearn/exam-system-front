package com.example.examsystembackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("archive_operation_log")
public class ArchiveOperationLog {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long archiveId;
    private Long userId;
    private String operation;
    private String description;
    private LocalDateTime createTime;
}