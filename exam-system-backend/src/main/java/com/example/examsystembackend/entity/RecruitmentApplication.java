package com.example.examsystembackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("recruitment_application")
public class RecruitmentApplication {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String gender;
    private Integer age;
    private String education;
    private String specialty;
    private String phone;
    private String email;
    private String positionApplied;
    private String workExperience;
    private String educationExperience;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}