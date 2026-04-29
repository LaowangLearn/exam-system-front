package com.example.examsystembackend.entity.his;

import com.baomidou.mybatisplus.annotation.*;
import com.example.examsystembackend.gov.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("his_patient")
public class Patient extends BaseEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private String patientId;
    private String patientName;
    private String idCard;
    private String socialSecurityNo;
    private String gender;
    private LocalDateTime birthDate;
    private String phone;
    private String emergencyContact;
    private String emergencyPhone;
    private String address;
    private String bloodType;
    private String allergicHistory;
    private String medicalHistory;
    private BigDecimal accountBalance;
    private String patientType;
    private String status;
}
