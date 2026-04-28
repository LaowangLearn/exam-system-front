package com.example.examsystembackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("member")
public class Member {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String memberNo;
    private String name;
    private String phone;
    private String email;
    private String gender;
    private LocalDate birthday;
    private String level;
    private BigDecimal points;
    private BigDecimal totalConsume;
    private Integer orderCount;
    private LocalDate registerDate;
    private LocalDate expireDate;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}