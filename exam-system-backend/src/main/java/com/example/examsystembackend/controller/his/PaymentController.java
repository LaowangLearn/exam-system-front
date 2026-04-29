package com.example.examsystembackend.controller.his;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.examsystembackend.gov.common.R;
import com.example.examsystembackend.entity.his.Payment;
import com.example.examsystembackend.service.his.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/his/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/page")
    public R<Page<Payment>> queryPage(
            @RequestParam(defaultValue = "1") Long pageNum,
            @RequestParam(defaultValue = "10") Long pageSize,
            @RequestParam(required = false) String patientId,
            @RequestParam(required = false) String businessType,
            @RequestParam(required = false) String paymentStatus) {
        return R.ok(paymentService.queryPage(pageNum, pageSize, patientId, businessType, paymentStatus));
    }

    @PostMapping
    public R<Payment> create(@RequestParam String patientId,
                            @RequestParam String businessType,
                            @RequestParam String sourceId,
                            @RequestParam BigDecimal amount,
                            @RequestParam String paymentMethod) {
        return R.ok(paymentService.createPayment(patientId, businessType, sourceId, amount, paymentMethod));
    }

    @PutMapping("/refund/{paymentId}")
    public R<Boolean> refund(@PathVariable String paymentId) {
        paymentService.refundPayment(paymentId);
        return R.ok(true);
    }
}
