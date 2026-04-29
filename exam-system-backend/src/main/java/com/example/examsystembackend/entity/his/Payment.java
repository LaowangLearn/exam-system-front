package com.example.examsystembackend.entity.his;

import com.baomidou.mybatisplus.annotation.*;
import com.example.examsystembackend.gov.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("his_payment")
public class Payment extends BaseEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private String paymentId;
    private String patientId;
    private String patientName;
    private String tradeNo;
    private String businessType;
    private String sourceId;
    private BigDecimal amount;
    private String paymentMethod;
    private String paymentStatus;
    private LocalDateTime paymentTime;
    private String operatorId;
    private String operatorName;
    private String remarks;
}
