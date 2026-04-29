package com.example.examsystembackend.entity.his;

import com.baomidou.mybatisplus.annotation.*;
import com.example.examsystembackend.gov.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("his_medical_record")
public class MedicalRecord extends BaseEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private String recordId;
    private String patientId;
    private String patientName;
    private String departmentId;
    private String departmentName;
    private String doctorId;
    private String doctorName;
    private LocalDateTime visitDate;
    private String chiefComplaint;
    private String presentIllness;
    private String pastHistory;
    private String physicalExamination;
    private String auxiliaryExamination;
    private String diagnosis;
    private String treatmentPlan;
    private String prescription;
    private String medicalAdvice;
    private String followUpDate;
    private String visitType;
}
