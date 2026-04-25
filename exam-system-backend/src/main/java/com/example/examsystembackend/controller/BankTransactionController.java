package com.example.examsystembackend.controller;

import com.example.examsystembackend.entity.BankTransaction;
import com.example.examsystembackend.service.BankTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finance/bank-transaction")
@RequiredArgsConstructor
public class BankTransactionController {

    private final BankTransactionService bankTransactionService;

    @GetMapping
    public List<BankTransaction> getAllBankTransactions() {
        return bankTransactionService.list();
    }

    @GetMapping("/{id}")
    public BankTransaction getBankTransactionById(@PathVariable Long id) {
        return bankTransactionService.getById(id);
    }

    @PostMapping
    public boolean createBankTransaction(@RequestBody BankTransaction bankTransaction) {
        return bankTransactionService.save(bankTransaction);
    }

    @PutMapping("/{id}")
    public boolean updateBankTransaction(@PathVariable Long id, @RequestBody BankTransaction bankTransaction) {
        bankTransaction.setId(id);
        return bankTransactionService.updateById(bankTransaction);
    }

    @DeleteMapping("/{id}")
    public boolean deleteBankTransaction(@PathVariable Long id) {
        return bankTransactionService.removeById(id);
    }
}