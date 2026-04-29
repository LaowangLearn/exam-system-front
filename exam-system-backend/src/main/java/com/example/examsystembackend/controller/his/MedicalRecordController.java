package com.example.examsystembackend.controller.his;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.examsystembackend.gov.common.R;
import com.example.examsystembackend.entity.his.MedicalRecord;
import com.example.examsystembackend.service.his.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/his/medicalRecord")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @GetMapping("/page")
    public R<Page<MedicalRecord>> queryPage(
            @RequestParam(defaultValue = "1") Long pageNum,
            @RequestParam(defaultValue = "10") Long pageSize,
            @RequestParam(required = false) String patientId,
            @RequestParam(required = false) String departmentId,
            @RequestParam(required = false) String doctorId) {
        return R.ok(medicalRecordService.queryPage(pageNum, pageSize, patientId, departmentId, doctorId));
    }

    @PostMapping
    public R<MedicalRecord> create(@RequestBody MedicalRecord medicalRecord) {
        return R.ok(medicalRecordService.createMedicalRecord(medicalRecord));
    }

    @GetMapping("/{id}")
    public R<MedicalRecord> getById(@PathVariable String id) {
        return R.ok(medicalRecordService.getById(id));
    }
}
