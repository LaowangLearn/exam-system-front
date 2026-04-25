package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.Voucher;
import com.example.examsystembackend.mapper.VoucherMapper;
import com.example.examsystembackend.service.VoucherService;
import org.springframework.stereotype.Service;

@Service
public class VoucherServiceImpl extends ServiceImpl<VoucherMapper, Voucher> implements VoucherService {
}