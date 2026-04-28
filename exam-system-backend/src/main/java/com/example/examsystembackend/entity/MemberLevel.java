package com.example.examsystembackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("member_level")
public class MemberLevel {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String levelCode;
    private String levelName;
    private BigDecimal minPoints;
    private BigDecimal discount;
    private Integer pointsRate;
    private String description;
    private Integer sortOrder;
    private LocalDateTime createTime;
}