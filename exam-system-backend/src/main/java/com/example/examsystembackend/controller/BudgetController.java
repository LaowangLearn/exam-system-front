package com.example.examsystembackend.controller;

import com.example.examsystembackend.entity.Budget;
import com.example.examsystembackend.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finance/budget")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @GetMapping
    public List<Budget> getAllBudgets() {
        return budgetService.list();
    }

    @GetMapping("/{id}")
    public Budget getBudgetById(@PathVariable Long id) {
        return budgetService.getById(id);
    }

    @PostMapping
    public boolean createBudget(@RequestBody Budget budget) {
        return budgetService.save(budget);
    }

    @PutMapping("/{id}")
    public boolean updateBudget(@PathVariable Long id, @RequestBody Budget budget) {
        budget.setId(id);
        return budgetService.updateById(budget);
    }

    @DeleteMapping("/{id}")
    public boolean deleteBudget(@PathVariable Long id) {
        return budgetService.removeById(id);
    }
}