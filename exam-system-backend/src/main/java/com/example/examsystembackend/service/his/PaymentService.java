package com.example.examsystembackend.service.his;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.examsystembackend.entity.his.Payment;

public interface PaymentService extends IService<Payment> {
    Page<Payment> queryPage(Long pageNum, Long pageSize, String patientId, String businessType, String paymentStatus);
    Payment createPayment(String patientId, String businessType, String sourceId, BigDecimal amount, String paymentMethod);
    void refundPayment(String paymentId);
}
