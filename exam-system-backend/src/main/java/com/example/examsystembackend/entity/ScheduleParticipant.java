package com.example.examsystembackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("schedule_participant")
public class ScheduleParticipant {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long scheduleId;
    private Long userId;
    private String status;
}
