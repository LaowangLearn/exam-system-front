package com.example.examsystembackend.controller;

import com.example.examsystembackend.entity.ExpenseReimbursement;
import com.example.examsystembackend.service.ExpenseReimbursementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finance/expense")
@RequiredArgsConstructor
public class ExpenseReimbursementController {

    private final ExpenseReimbursementService expenseReimbursementService;

    @GetMapping
    public List<ExpenseReimbursement> getAllExpenses() {
        return expenseReimbursementService.list();
    }

    @GetMapping("/{id}")
    public ExpenseReimbursement getExpenseById(@PathVariable Long id) {
        return expenseReimbursementService.getById(id);
    }

    @PostMapping
    public boolean createExpense(@RequestBody ExpenseReimbursement expense) {
        return expenseReimbursementService.save(expense);
    }

    @PutMapping("/{id}")
    public boolean updateExpense(@PathVariable Long id, @RequestBody ExpenseReimbursement expense) {
        expense.setId(id);
        return expenseReimbursementService.updateById(expense);
    }

    @DeleteMapping("/{id}")
    public boolean deleteExpense(@PathVariable Long id) {
        return expenseReimbursementService.removeById(id);
    }
}