package com.example.examsystembackend.controller;

import com.example.examsystembackend.entity.CustomerTask;
import com.example.examsystembackend.service.CustomerTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/crm/tasks")
@RequiredArgsConstructor
public class CustomerTaskController {

    private final CustomerTaskService customerTaskService;

    @GetMapping("/customer/{customerId}")
    public List<CustomerTask> getByCustomerId(@PathVariable Long customerId) {
        return customerTaskService.getByCustomerId(customerId);
    }

    @GetMapping("/assignee/{assigneeId}")
    public List<CustomerTask> getByAssigneeId(@PathVariable Long assigneeId) {
        return customerTaskService.getByAssigneeId(assigneeId);
    }

    @GetMapping("/{id}")
    public CustomerTask getById(@PathVariable Long id) {
        return customerTaskService.getById(id);
    }

    @PostMapping
    public CustomerTask create(@RequestBody CustomerTask task) {
        task.setCreateTime(LocalDateTime.now());
        customerTaskService.save(task);
        return task;
    }

    @PutMapping("/{id}")
    public CustomerTask update(@PathVariable Long id, @RequestBody CustomerTask task) {
        task.setId(id);
        task.setUpdateTime(LocalDateTime.now());
        customerTaskService.updateById(task);
        return task;
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return customerTaskService.removeById(id);
    }
}