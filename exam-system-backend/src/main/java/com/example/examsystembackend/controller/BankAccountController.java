package com.example.examsystembackend.controller;

import com.example.examsystembackend.entity.BankAccount;
import com.example.examsystembackend.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finance/bank-account")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @GetMapping
    public List<BankAccount> getAllBankAccounts() {
        return bankAccountService.list();
    }

    @GetMapping("/{id}")
    public BankAccount getBankAccountById(@PathVariable Long id) {
        return bankAccountService.getById(id);
    }

    @PostMapping
    public boolean createBankAccount(@RequestBody BankAccount bankAccount) {
        return bankAccountService.save(bankAccount);
    }

    @PutMapping("/{id}")
    public boolean updateBankAccount(@PathVariable Long id, @RequestBody BankAccount bankAccount) {
        bankAccount.setId(id);
        return bankAccountService.updateById(bankAccount);
    }

    @DeleteMapping("/{id}")
    public boolean deleteBankAccount(@PathVariable Long id) {
        return bankAccountService.removeById(id);
    }
}