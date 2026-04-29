package com.example.examsystembackend.service.his.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.his.Prescription;
import com.example.examsystembackend.mapper.his.PrescriptionMapper;
import com.example.examsystembackend.service.his.PrescriptionService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.time.LocalDateTime;

@Service
public class PrescriptionServiceImpl extends ServiceImpl<PrescriptionMapper, Prescription> implements PrescriptionService {

    @Override
    public Page<Prescription> queryPage(Long pageNum, Long pageSize, String patientId, String doctorId, String dispensingStatus) {
        Page<Prescription> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Prescription> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(patientId)) {
            wrapper.eq(Prescription::getPatientId, patientId);
        }
        if (StringUtils.hasText(doctorId)) {
            wrapper.eq(Prescription::getDoctorId, doctorId);
        }
        if (StringUtils.hasText(dispensingStatus)) {
            wrapper.eq(Prescription::getDispensingStatus, dispensingStatus);
        }
        wrapper.orderByDesc(Prescription::getPrescriptionDate);
        return page(page, wrapper);
    }

    @Override
    public Prescription createPrescription(Prescription prescription) {
        prescription.setPrescriptionDate(LocalDateTime.now());
        prescription.setDispensingStatus("UNDISPENSED");
        prescription.setPaymentStatus("UNPAID");
        save(prescription);
        return prescription;
    }

    @Override
    public void dispensePrescription(String prescriptionId, String pharmacistId) {
        Prescription prescription = getById(prescriptionId);
        if (prescription != null) {
            prescription.setDispensingStatus("DISPENSED");
            prescription.setDispensingPharmacistId(pharmacistId);
            prescription.setDispensingTime(LocalDateTime.now());
            updateById(prescription);
        }
    }
}
