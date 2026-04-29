package com.example.examsystembackend.service.his.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.his.Patient;
import com.example.examsystembackend.mapper.his.PatientMapper;
import com.example.examsystembackend.service.his.PatientService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PatientServiceImpl extends ServiceImpl<PatientMapper, Patient> implements PatientService {

    @Override
    public Page<Patient> queryPage(Long pageNum, Long pageSize, String patientName, String idCard, String phone) {
        Page<Patient> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Patient> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(patientName)) {
            wrapper.like(Patient::getPatientName, patientName);
        }
        if (StringUtils.hasText(idCard)) {
            wrapper.eq(Patient::getIdCard, idCard);
        }
        if (StringUtils.hasText(phone)) {
            wrapper.eq(Patient::getPhone, phone);
        }
        wrapper.orderByDesc(Patient::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    public Patient getPatientByIdCard(String idCard) {
        LambdaQueryWrapper<Patient> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Patient::getIdCard, idCard);
        return getOne(wrapper);
    }
}
