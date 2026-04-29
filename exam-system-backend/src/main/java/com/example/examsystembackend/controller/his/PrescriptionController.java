package com.example.examsystembackend.controller.his;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.examsystembackend.gov.common.R;
import com.example.examsystembackend.entity.his.Prescription;
import com.example.examsystembackend.service.his.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/his/prescription")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping("/page")
    public R<Page<Prescription>> queryPage(
            @RequestParam(defaultValue = "1") Long pageNum,
            @RequestParam(defaultValue = "10") Long pageSize,
            @RequestParam(required = false) String patientId,
            @RequestParam(required = false) String doctorId,
            @RequestParam(required = false) String dispensingStatus) {
        return R.ok(prescriptionService.queryPage(pageNum, pageSize, patientId, doctorId, dispensingStatus));
    }

    @PostMapping
    public R<Prescription> create(@RequestBody Prescription prescription) {
        return R.ok(prescriptionService.createPrescription(prescription));
    }

    @PutMapping("/dispense/{prescriptionId}")
    public R<Boolean> dispense(@PathVariable String prescriptionId, @RequestParam String pharmacistId) {
        prescriptionService.dispensePrescription(prescriptionId, pharmacistId);
        return R.ok(true);
    }
}
