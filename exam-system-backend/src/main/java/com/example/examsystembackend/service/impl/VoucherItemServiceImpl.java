package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.VoucherItem;
import com.example.examsystembackend.mapper.VoucherItemMapper;
import com.example.examsystembackend.service.VoucherItemService;
import org.springframework.stereotype.Service;

@Service
public class VoucherItemServiceImpl extends ServiceImpl<VoucherItemMapper, VoucherItem> implements VoucherItemService {
}