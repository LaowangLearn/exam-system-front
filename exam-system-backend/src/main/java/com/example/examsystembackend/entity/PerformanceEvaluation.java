package com.example.examsystembackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("performance_evaluation")
public class PerformanceEvaluation {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long evaluatorId;
    private String period;
    private Double score;
    private String strengths;
    private String weaknesses;
    private String suggestions;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}