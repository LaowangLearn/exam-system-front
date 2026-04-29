package com.example.examsystembackend.service.his;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.examsystembackend.entity.his.MedicalRecord;

public interface MedicalRecordService extends IService<MedicalRecord> {
    Page<MedicalRecord> queryPage(Long pageNum, Long pageSize, String patientId, String departmentId, String doctorId);
    MedicalRecord createMedicalRecord(MedicalRecord medicalRecord);
}
