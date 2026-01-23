package com.example.examsystembackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.examsystembackend.enums.ExamStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_exam")
public class Exam {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String title;

    private String description;

    private String createUser;

    private ExamStatus status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}