package com.example.examsystembackend.entity.his;

import com.baomidou.mybatisplus.annotation.*;
import com.example.examsystembackend.gov.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("his_registration")
public class Registration extends BaseEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private String registrationId;
    private String patientId;
    private String patientName;
    private String departmentId;
    private String departmentName;
    private String doctorId;
    private String doctorName;
    private LocalDateTime appointmentTime;
    private String visitStatus;
    private String illnessDescription;
    private String preliminaryDiagnosis;
    private String referralDepartment;
    private BigDecimal registrationFee;
    private String paymentStatus;
    private String medicalCardNo;
}
