package com.example.examsystembackend.service.his.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.his.Payment;
import com.example.examsystembackend.mapper.his.PaymentMapper;
import com.example.examsystembackend.service.his.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, Payment> implements PaymentService {

    @Override
    public Page<Payment> queryPage(Long pageNum, Long pageSize, String patientId, String businessType, String paymentStatus) {
        Page<Payment> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Payment> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(patientId)) {
            wrapper.eq(Payment::getPatientId, patientId);
        }
        if (StringUtils.hasText(businessType)) {
            wrapper.eq(Payment::getBusinessType, businessType);
        }
        if (StringUtils.hasText(paymentStatus)) {
            wrapper.eq(Payment::getPaymentStatus, paymentStatus);
        }
        wrapper.orderByDesc(Payment::getPaymentTime);
        return page(page, wrapper);
    }

    @Override
    public Payment createPayment(String patientId, String businessType, String sourceId, BigDecimal amount, String paymentMethod) {
        Payment payment = new Payment();
        payment.setPatientId(patientId);
        payment.setBusinessType(businessType);
        payment.setSourceId(sourceId);
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentStatus("PENDING");
        payment.setPaymentTime(LocalDateTime.now());
        payment.setTradeNo("PAY" + UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase());
        save(payment);
        return payment;
    }

    @Override
    public void refundPayment(String paymentId) {
        Payment payment = getById(paymentId);
        if (payment != null) {
            payment.setPaymentStatus("REFUNDED");
            updateById(payment);
        }
    }
}
