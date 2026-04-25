package com.example.examsystembackend.controller;

import com.example.examsystembackend.entity.TaxDeclaration;
import com.example.examsystembackend.service.TaxDeclarationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finance/tax")
@RequiredArgsConstructor
public class TaxDeclarationController {

    private final TaxDeclarationService taxDeclarationService;

    @GetMapping
    public List<TaxDeclaration> getAllTaxDeclarations() {
        return taxDeclarationService.list();
    }

    @GetMapping("/{id}")
    public TaxDeclaration getTaxDeclarationById(@PathVariable Long id) {
        return taxDeclarationService.getById(id);
    }

    @PostMapping
    public boolean createTaxDeclaration(@RequestBody TaxDeclaration taxDeclaration) {
        return taxDeclarationService.save(taxDeclaration);
    }

    @PutMapping("/{id}")
    public boolean updateTaxDeclaration(@PathVariable Long id, @RequestBody TaxDeclaration taxDeclaration) {
        taxDeclaration.setId(id);
        return taxDeclarationService.updateById(taxDeclaration);
    }

    @DeleteMapping("/{id}")
    public boolean deleteTaxDeclaration(@PathVariable Long id) {
        return taxDeclarationService.removeById(id);
    }
}