package com.example.examsystembackend.controller;

import com.example.examsystembackend.entity.Voucher;
import com.example.examsystembackend.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finance/voucher")
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    @GetMapping
    public List<Voucher> getAllVouchers() {
        return voucherService.list();
    }

    @GetMapping("/{id}")
    public Voucher getVoucherById(@PathVariable Long id) {
        return voucherService.getById(id);
    }

    @PostMapping
    public boolean createVoucher(@RequestBody Voucher voucher) {
        return voucherService.save(voucher);
    }

    @PutMapping("/{id}")
    public boolean updateVoucher(@PathVariable Long id, @RequestBody Voucher voucher) {
        voucher.setId(id);
        return voucherService.updateById(voucher);
    }

    @DeleteMapping("/{id}")
    public boolean deleteVoucher(@PathVariable Long id) {
        return voucherService.removeById(id);
    }
}