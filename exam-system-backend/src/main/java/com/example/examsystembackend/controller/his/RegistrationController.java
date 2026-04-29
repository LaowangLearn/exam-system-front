package com.example.examsystembackend.controller.his;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.examsystembackend.gov.common.R;
import com.example.examsystembackend.entity.his.Registration;
import com.example.examsystembackend.service.his.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/his/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/page")
    public R<Page<Registration>> queryPage(
            @RequestParam(defaultValue = "1") Long pageNum,
            @RequestParam(defaultValue = "10") Long pageSize,
            @RequestParam(required = false) String patientName,
            @RequestParam(required = false) String departmentId,
            @RequestParam(required = false) String visitStatus) {
        return R.ok(registrationService.queryPage(pageNum, pageSize, patientName, departmentId, visitStatus));
    }

    @PostMapping
    public R<Registration> create(@RequestBody Registration registration) {
        return R.ok(registrationService.createRegistration(registration));
    }

    @PutMapping("/cancel/{registrationId}")
    public R<Boolean> cancel(@PathVariable String registrationId) {
        registrationService.cancelRegistration(registrationId);
        return R.ok(true);
    }
}
