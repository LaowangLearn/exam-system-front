package com.example.examsystembackend.service.his;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.examsystembackend.entity.his.Patient;

public interface PatientService extends IService<Patient> {
    Page<Patient> queryPage(Long pageNum, Long pageSize, String patientName, String idCard, String phone);
    Patient getPatientByIdCard(String idCard);
}
