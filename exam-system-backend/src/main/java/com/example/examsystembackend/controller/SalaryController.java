package com.example.examsystembackend.controller;

import com.example.examsystembackend.entity.Salary;
import com.example.examsystembackend.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hrm/salary")
@RequiredArgsConstructor
public class SalaryController {

    private final SalaryService salaryService;

    @GetMapping
    public List<Salary> getAllSalaries() {
        return salaryService.list();
    }

    @GetMapping("/{id}")
    public Salary getSalaryById(@PathVariable Long id) {
        return salaryService.getById(id);
    }

    @PostMapping
    public boolean createSalary(@RequestBody Salary salary) {
        // 计算总工资
        double total = salary.getBaseSalary() + salary.getBonus() + salary.getAllowance() - salary.getDeduction();
        salary.setTotalSalary(total);
        return salaryService.save(salary);
    }

    @PutMapping("/{id}")
    public boolean updateSalary(@PathVariable Long id, @RequestBody Salary salary) {
        salary.setId(id);
        // 重新计算总工资
        double total = salary.getBaseSalary() + salary.getBonus() + salary.getAllowance() - salary.getDeduction();
        salary.setTotalSalary(total);
        return salaryService.updateById(salary);
    }

    @DeleteMapping("/{id}")
    public boolean deleteSalary(@PathVariable Long id) {
        return salaryService.removeById(id);
    }
}
