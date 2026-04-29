package com.example.examsystembackend.controller.his;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.examsystembackend.gov.common.R;
import com.example.examsystembackend.entity.his.Patient;
import com.example.examsystembackend.service.his.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/his/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/page")
    public R<Page<Patient>> queryPage(
            @RequestParam(defaultValue = "1") Long pageNum,
            @RequestParam(defaultValue = "10") Long pageSize,
            @RequestParam(required = false) String patientName,
            @RequestParam(required = false) String idCard,
            @RequestParam(required = false) String phone) {
        return R.ok(patientService.queryPage(pageNum, pageSize, patientName, idCard, phone));
    }

    @GetMapping("/idCard/{idCard}")
    public R<Patient> getByIdCard(@PathVariable String idCard) {
        return R.ok(patientService.getPatientByIdCard(idCard));
    }

    @PostMapping
    public R<Boolean> save(@RequestBody Patient patient) {
        return R.ok(patientService.save(patient));
    }

    @PutMapping
    public R<Boolean> update(@RequestBody Patient patient) {
        return R.ok(patientService.updateById(patient));
    }

    @DeleteMapping("/{id}")
    public R<Boolean> delete(@PathVariable String id) {
        return R.ok(patientService.removeById(id));
    }
}
