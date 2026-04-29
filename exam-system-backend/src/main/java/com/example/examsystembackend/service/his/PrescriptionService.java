package com.example.examsystembackend.service.his;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.examsystembackend.entity.his.Prescription;

public interface PrescriptionService extends IService<Prescription> {
    Page<Prescription> queryPage(Long pageNum, Long pageSize, String patientId, String doctorId, String dispensingStatus);
    Prescription createPrescription(Prescription prescription);
    void dispensePrescription(String prescriptionId, String pharmacistId);
}
