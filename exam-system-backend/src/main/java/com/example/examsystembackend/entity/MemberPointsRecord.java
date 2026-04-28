package com.example.examsystembackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("member_points_record")
public class MemberPointsRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long memberId;
    private String type;
    private BigDecimal points;
    private BigDecimal balance;
    private String source;
    private String remark;
    private LocalDateTime createTime;
}