package com.example.examsystembackend.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.examsystembackend.entity.Approval;
import com.example.examsystembackend.service.ApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/oa/approvals")
@RequiredArgsConstructor
public class ApprovalController {

    private final ApprovalService approvalService;

    @GetMapping
    public List<Approval> getAllApprovals() {
        return approvalService.list();
    }

    @GetMapping("/{id}")
    public Approval getApprovalById(@PathVariable Long id) {
        return approvalService.getById(id);
    }

    @PostMapping
    public boolean createApproval(@RequestBody Approval approval) {
        return approvalService.save(approval);
    }

    @PutMapping("/{id}")
    public boolean updateApproval(@PathVariable Long id, @RequestBody Approval approval) {
        approval.setId(id);
        return approvalService.updateById(approval);
    }

    @DeleteMapping("/{id}")
    public boolean deleteApproval(@PathVariable Long id) {
        return approvalService.removeById(id);
    }
}
