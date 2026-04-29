package com.example.examsystembackend.entity.his;

import com.baomidou.mybatisplus.annotation.*;
import com.example.examsystembackend.gov.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("his_prescription")
public class Prescription extends BaseEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private String prescriptionId;
    private String patientId;
    private String patientName;
    private String doctorId;
    private String doctorName;
    private String departmentId;
    private LocalDateTime prescriptionDate;
    private String diagnosis;
    private String prescriptionType;
    private BigDecimal totalAmount;
    private String paymentStatus;
    private String dispensingStatus;
    private String dispensingPharmacistId;
    private LocalDateTime dispensingTime;
    private String notes;
}
