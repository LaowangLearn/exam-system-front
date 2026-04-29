package com.example.examsystembackend.service.his.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.his.MedicalRecord;
import com.example.examsystembackend.mapper.his.MedicalRecordMapper;
import com.example.examsystembackend.service.his.MedicalRecordService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MedicalRecordServiceImpl extends ServiceImpl<MedicalRecordMapper, MedicalRecord> implements MedicalRecordService {

    @Override
    public Page<MedicalRecord> queryPage(Long pageNum, Long pageSize, String patientId, String departmentId, String doctorId) {
        Page<MedicalRecord> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<MedicalRecord> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(patientId)) {
            wrapper.eq(MedicalRecord::getPatientId, patientId);
        }
        if (StringUtils.hasText(departmentId)) {
            wrapper.eq(MedicalRecord::getDepartmentId, departmentId);
        }
        if (StringUtils.hasText(doctorId)) {
            wrapper.eq(MedicalRecord::getDoctorId, doctorId);
        }
        wrapper.orderByDesc(MedicalRecord::getVisitDate);
        return page(page, wrapper);
    }

    @Override
    public MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) {
        save(medicalRecord);
        return medicalRecord;
    }
}
