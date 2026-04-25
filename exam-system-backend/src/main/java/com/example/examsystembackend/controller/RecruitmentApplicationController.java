package com.example.examsystembackend.controller;

import com.example.examsystembackend.entity.RecruitmentApplication;
import com.example.examsystembackend.service.RecruitmentApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hrm/recruitment")
@RequiredArgsConstructor
public class RecruitmentApplicationController {

    private final RecruitmentApplicationService recruitmentApplicationService;

    @GetMapping
    public List<RecruitmentApplication> getAllApplications() {
        return recruitmentApplicationService.list();
    }

    @GetMapping("/{id}")
    public RecruitmentApplication getApplicationById(@PathVariable Long id) {
        return recruitmentApplicationService.getById(id);
    }

    @PostMapping
    public boolean createApplication(@RequestBody RecruitmentApplication application) {
        return recruitmentApplicationService.save(application);
    }

    @PutMapping("/{id}")
    public boolean updateApplication(@PathVariable Long id, @RequestBody RecruitmentApplication application) {
        application.setId(id);
        return recruitmentApplicationService.updateById(application);
    }

    @DeleteMapping("/{id}")
    public boolean deleteApplication(@PathVariable Long id) {
        return recruitmentApplicationService.removeById(id);
    }
}
