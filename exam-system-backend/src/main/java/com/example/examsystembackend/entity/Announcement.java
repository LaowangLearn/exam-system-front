package com.example.examsystembackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("announcement")
public class Announcement {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String content;
    private String type;
    private String priority;
    private Long publisherId;
    private LocalDateTime publishTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private Integer viewCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}