package com.example.examsystembackend.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.examsystembackend.entity.Supplier;
import com.example.examsystembackend.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/scm/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping
    public IPage<Supplier> list(@RequestParam(defaultValue = "1") int page, 
                                @RequestParam(defaultValue = "10") int size) {
        return supplierService.page(new Page<>(page, size));
    }

    @GetMapping("/{id}")
    public Supplier getById(@PathVariable Long id) {
        return supplierService.getById(id);
    }

    @PostMapping
    public Supplier create(@RequestBody Supplier supplier) {
        supplier.setSupplierCode(supplierService.generateSupplierCode());
        supplier.setCreateTime(LocalDateTime.now());
        supplierService.save(supplier);
        return supplier;
    }

    @PutMapping("/{id}")
    public Supplier update(@PathVariable Long id, @RequestBody Supplier supplier) {
        supplier.setId(id);
        supplier.setUpdateTime(LocalDateTime.now());
        supplierService.updateById(supplier);
        return supplier;
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return supplierService.removeById(id);
    }

    @GetMapping("/statistics")
    public Map<String, Object> statistics() {
        Map<String, Object> result = new HashMap<>();
        result.put("total", supplierService.count());
        result.put("active", supplierService.lambdaQuery().eq(Supplier::getStatus, "ACTIVE").count());
        return result;
    }
}