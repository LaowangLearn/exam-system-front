package com.example.examsystembackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("archive")
public class Archive {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String archiveNo;
    private String title;
    private Long categoryId;
    private String content;
    private String filePath;
    private String fileName;
    private Long fileSize;
    private String secretLevel;
    private String status;
    private Long creatorId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime destroyTime;
}