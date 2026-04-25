package com.example.examsystembackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("training_participant")
public class TrainingParticipant {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long trainingId;
    private Long userId;
    private String attendance;
    private Double score;
    private String feedback;
}